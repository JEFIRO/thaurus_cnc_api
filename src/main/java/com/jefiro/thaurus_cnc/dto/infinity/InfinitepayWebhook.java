package com.jefiro.thaurus_cnc.dto.infinity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfinitepayWebhook {
    private String invoice_slug;
    private Double amount;
    private Double paid_amount;
    private Double installments;
    private String capture_method;
    private String transaction_nsu;
    private String order_nsu;
    private String receipt_url;
}
