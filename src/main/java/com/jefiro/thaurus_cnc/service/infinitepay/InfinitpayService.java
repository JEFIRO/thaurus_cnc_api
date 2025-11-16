package com.jefiro.thaurus_cnc.service.infinitepay;

import com.jefiro.thaurus_cnc.dto.infinity.InfinitypayDTO;
import com.jefiro.thaurus_cnc.dto.infinity.InfinitypayItens;
import com.jefiro.thaurus_cnc.dto.pagamento.PagamentoResponse;
import com.jefiro.thaurus_cnc.infra.exception.RecursoNaoEncontradoException;
import com.jefiro.thaurus_cnc.infra.exception.StatusInvalidoException;
import com.jefiro.thaurus_cnc.model.Infinitepay.DadosPagamento;
import com.jefiro.thaurus_cnc.dto.infinity.InfinitepayWebhook;
import com.jefiro.thaurus_cnc.model.Infinitepay.StatusPagamento;
import com.jefiro.thaurus_cnc.model.Infinitepay.Pagamentos;
import com.jefiro.thaurus_cnc.model.Pedido;
import com.jefiro.thaurus_cnc.model.PedidoItem; // Import necessário
// Importe o PagamentoRepository
import com.jefiro.thaurus_cnc.model.StatusPedido;
import com.jefiro.thaurus_cnc.repository.PagamentoRepository;
import com.jefiro.thaurus_cnc.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class InfinitpayService {
    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private InfinitypayConfig infinitypayConfig;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    private final WebClient webClient;

    public InfinitpayService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://api.infinitepay.io/invoices/public/checkout/links").build();
    }

    public void updateLembretePagamento1(long id) {
        var pagamento = pagamentoRepository.findById(id).orElseThrow(RecursoNaoEncontradoException::new);

        pagamento.setLembretеsPrimeiro(pagamento.getLembretеsPrimeiro() + 1);
        pagamento.setUltimoLembretePrimeiro(LocalDateTime.now());

        pagamentoRepository.save(pagamento);
    }

    public void updateLembretePagamento2(long id) {
        var pagamento = pagamentoRepository.findById(id).orElseThrow(RecursoNaoEncontradoException::new);

        pagamento.setLembretesSegundo(pagamento.getLembretesSegundo() + 1);
        pagamento.setUltimoLembreteSegundo(LocalDateTime.now());

        pagamentoRepository.save(pagamento);
    }

    private Pedido manipulaPedido(Long id, Double valorAdicinal) {
        Pedido pedido = pedidoService.get(id);
        Pagamentos pagamentos = pedido.getPagamentos();

        double diferencaAdicional = 0;
        if (valorAdicinal != null && valorAdicinal > 0) {
            double customizacaoAnterior = (pedido.getValor_customizacao() != null ? pedido.getValor_customizacao() : 0);
            pedido.setValor_customizacao(customizacaoAnterior + valorAdicinal);
            diferencaAdicional = valorAdicinal; // A diferença a ser adicionada ao restante
        }


        double totalItens = pedido.getItens().stream()
                .mapToDouble(PedidoItem::getValor)
                .sum();

        double frete = (pedido.getFrete() != null && pedido.getFrete().valor_frete() != null)
                ? pedido.getFrete().valor_frete() : 0;

        double customizacao = (pedido.getValor_customizacao() != null ? pedido.getValor_customizacao() : 0);

        double novoValorTotalPedido = totalItens + frete + customizacao;

        if (Math.abs(novoValorTotalPedido - (pedido.getValor_total() != null ? pedido.getValor_total() : 0)) > 0.01 || diferencaAdicional > 0) {

            pedido.setValor_total(novoValorTotalPedido);

            if (pagamentos != null) {
                pagamentos.setValorTotal(novoValorTotalPedido);

                // Se for o primeiro pagamento, o restante é o total
                if (pagamentos.getStatus().equals(StatusPagamento.PENDING_PAYMENT)) {
                    pagamentos.setValorRestante(novoValorTotalPedido);
                } else {
                    // Se já pagou (PAYMENT_ENTRY), adiciona a diferença (o valorAdicinal) ao valor restante
                    pagamentos.setValorRestante(pagamentos.getValorRestante() + diferencaAdicional);
                }
                pagamentoRepository.save(pagamentos); // Salva as mudanças em Pagamentos
            }
            pedido = pedidoService.upSimples(pedido);
        }

        return pedido;
    }


    /**
     * MÉTODO GERARLINK (Lógica de 50/50)
     * Implementa a lógica de 2 etapas.
     * Envia um item de "Sinal" (50%) ou "Restante" (valor que falta).
     */
    public Mono<String> gerarLink(Long pedidoId, Double valorAdicinal) {
        Pedido pedido = manipulaPedido(pedidoId, valorAdicinal);
        Pagamentos pagamentos = pedido.getPagamentos();

        List<InfinitypayItens> items = new ArrayList<>();

        if (pagamentos.getStatus().equals(StatusPagamento.PENDING_PAYMENT)) {

            // Valor de entrada (50% do total)
            double valorEntrada = pagamentos.getValorTotal() / 2.0;

            // Arredondar para 2 casas decimais para evitar problemas com centavos
            valorEntrada = Math.round(valorEntrada * 100.0) / 100.0;

            items.add(new InfinitypayItens(
                    1,
                    valorEntrada,
                    "Pagamento de 50% (Sinal) - Pedido " + pedido.getId()
            ));

            // Se for o SEGUNDO pagamento (PAYMENT_ENTRY)
        } else if (pagamentos.getStatus().equals(StatusPagamento.PAYMENT_ENTRY)) {

            // O valor a ser pago é o valor restante.
            // O manipulaPedido já ajustou o valorRestante se houve valorAdicinal.
            double valorRestante = pagamentos.getValorRestante();
            valorRestante = Math.round(valorRestante * 100.0) / 100.0;

            // Garante que não estamos cobrando 0 ou valor negativo se algo der errado
            if (valorRestante <= 0.01) { // Usando margem de 1 centavo
                throw new StatusInvalidoException("O valor restante deste pedido é zero ou negativo.");
            }

            items.add(new InfinitypayItens(
                    1,
                    valorRestante,
                    "Pagamento Restante (50%) - Pedido " + pedido.getId()
            ));

            // Se o pagamento já estiver completo, não gera link
        } else if (pagamentos.getStatus().equals(StatusPagamento.PAYMENT_COMPLETED)) {
            throw new StatusInvalidoException("Este pedido já foi totalmente pago.");
        }

        // A lista 'items' agora contém o item correto (Sinal ou Restante).

        InfinitypayDTO infinitypayDTO = new InfinitypayDTO(
                items, // 'items' agora tem o valor parcial (Sinal ou Restante)
                pedido.getId_Pedido(),
                infinitypayConfig.getHandle(),
                infinitypayConfig.getRedirectUrl(),
                infinitypayConfig.getWebhookUrl()
        );

        System.out.println(infinitypayDTO);

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(infinitypayDTO)
                .retrieve()
                .bodyToMono(String.class);
    }

    public PagamentoResponse webhook(InfinitepayWebhook payload) {
        Pedido pedido = pedidoService.findByUuid(payload.getOrder_nsu());
        Pagamentos pagamento = pedido.getPagamentos();

        List<DadosPagamento> dadosPagamentos = pagamento.getDadosPagamentos();
        if (dadosPagamentos == null) {
            dadosPagamentos = new ArrayList<>();
        }

        DadosPagamento novoDado = new DadosPagamento(payload, pagamento);

        dadosPagamentos.add(novoDado);
        pagamento.setDadosPagamentos(dadosPagamentos);

        double valorPago = payload.getAmount();
        pagamento.setValorRestante(pagamento.getValorRestante() - valorPago);

        if (pagamento.getValorPago() == null) {
            pagamento.setValorPago(0 + valorPago);
        } else {
            pagamento.setValorPago(pagamento.getValorPago() + valorPago);
        }

        if (pagamento.getStatus().equals(StatusPagamento.PENDING_PAYMENT)) {
            pagamento.setStatus(StatusPagamento.PAYMENT_ENTRY);
            pedido.setStatus(StatusPedido.IN_PRODUCTION);
        }

        if (pagamento.getValorRestante() <= 0.01) {
            pagamento.setStatus(StatusPagamento.PAYMENT_COMPLETED);
            pagamento.setValorRestante(0.0);
            pedido.setStatus(StatusPedido.PREPARING_FOR_DELIVERY);
        }

        pedidoService.upSimples(pedido);

        return new PagamentoResponse(pagamentoRepository.save(pagamento));
    }


    public Pagamentos get(Long id) {
        return pagamentoRepository.findById(id).orElseThrow(RecursoNaoEncontradoException::new);
    }
}