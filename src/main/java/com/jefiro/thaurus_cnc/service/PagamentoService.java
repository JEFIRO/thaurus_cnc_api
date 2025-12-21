package com.jefiro.thaurus_cnc.service;


import com.jefiro.thaurus_cnc.dto.pagamento.PagamentoResponse;
import com.jefiro.thaurus_cnc.dto.pagamento.PagamentoResquest;
import com.jefiro.thaurus_cnc.infra.exception.RecursoNaoEncontradoException;
import com.jefiro.thaurus_cnc.model.Pedido;
import com.jefiro.thaurus_cnc.model.pagamento.MetodoPagamento;
import com.jefiro.thaurus_cnc.model.pagamento.Pagamentos;
import com.jefiro.thaurus_cnc.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {
    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private PagamentoRepository pagamentos;

    public PagamentoResponse pagarPedido(Long idPedido, PagamentoResquest pagamentoResquest) {
        Pedido pedido = pedidoService.get(idPedido);

        Pagamentos pagamentos = new Pagamentos(pagamentoResquest, pedido);

        pagamentos.setValorTotal(pedido.getValor_total());

        var restante = pedido.getValor_total() - pagamentoResquest.valorPago();

        if (restante < 0) restante = 0;
        pagamentos.setValorRestante(restante);

        pedido.setPagamentos(pagamentos);

        pedido = pedidoService.upSimples(pedido);
        return new PagamentoResponse(pedido.getPagamentos());

    }


    public PagamentoResponse buscarPagamento(Long id) {
        Pagamentos pagamento = pagamentos.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Pagamento não encontrado"));
        return new PagamentoResponse(pagamento);
    }

    public PagamentoResponse atualizarPedido(Long id, PagamentoResquest pagamentoResquest) {
        Pedido pedido = pedidoService.get(id);

        Pagamentos pagamentos = pedido.getPagamentos();

        if (pagamentoResquest.observacao() != null) {
            pagamentos.setObservacao(pagamentoResquest.observacao());
        }

        pagamentos.setValorPago(pagamentoResquest.valorPago());
        pagamentos.setMetodoPagamento(MetodoPagamento.valueOf(pagamentoResquest.metodoPagamento()));

        pagamentos.setValorTotal(pedido.getValor_total());

        pagamentos = calcValores(pagamentos);

        pedido.setPagamentos(pagamentos);

        pedido = pedidoService.upSimples(pedido);
        return new PagamentoResponse(pedido.getPagamentos());
    }

    private Pagamentos calcValores(Pagamentos pagamentos) {
        if (pagamentos.getValorPago() != null && pagamentos.getValorTotal() != null) {

            double valorPago = pagamentos.getValorPago();
            double valorTotal = pagamentos.getValorTotal();

            double restante = valorTotal - valorPago;

            if (restante < 0) restante = 0;
            pagamentos.setValorRestante(restante);
            return pagamentos;
        }
        return pagamentos;
    }

}
