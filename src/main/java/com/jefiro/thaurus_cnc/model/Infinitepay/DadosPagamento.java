package com.jefiro.thaurus_cnc.model.Infinitepay;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table
public class DadosPagamento {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @ToString.Exclude
    private Pagamentos pagamento;
    private String transaction_nsu;
    private String reciboCliente;
    private String formaPagamento;
    private LocalDateTime dataPagamento;
    private Double valorPago;
    private String recibo;

    public DadosPagamento(InfinitepayWebhook webhook) {
        this.transaction_nsu = webhook.getTransaction_nsu();
        this.reciboCliente = webhook.getReceipt_url();
        this.formaPagamento = webhook.getCapture_method();
        this.dataPagamento = LocalDateTime.now();
        this.valorPago = webhook.getAmount();
        this.recibo = webhook.getReceipt_url();
    }
}
