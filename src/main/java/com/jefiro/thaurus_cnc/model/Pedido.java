package com.jefiro.thaurus_cnc.model;

import com.jefiro.thaurus_cnc.dto.Frete;
import com.jefiro.thaurus_cnc.model.pagamento.Pagamentos;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
@Table
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String id_Pedido;
    @ManyToOne
    private Cliente cliente;
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<PedidoItem> itens = new ArrayList<>();
    private Double valor_total;
    private Double valor_customizacao;
    @Enumerated(EnumType.STRING)
    private StatusPedido status;
    @Embedded
    private Frete frete;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pagamentos_id", referencedColumnName = "id")
    @ToString.Exclude
    private Pagamentos pagamentos;
    private LocalDateTime data_pedido;
    private LocalDateTime data_finalizacao;
    private boolean ativo;

    public Pedido() {
        this.id_Pedido = UUID.randomUUID().toString();
         this.status = StatusPedido.LAYOUT_PENDING;
        this.data_pedido = LocalDateTime.now();
        this.data_finalizacao = LocalDateTime.now();
        this.ativo = true;
    }
}
