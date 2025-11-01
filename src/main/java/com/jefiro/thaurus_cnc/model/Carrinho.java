package com.jefiro.thaurus_cnc.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Carrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Cliente cliente;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarrinhoItem> itens = new ArrayList<>();

    private LocalDateTime criadoEm = LocalDateTime.now();

    public void adicionarItem(CarrinhoItem item) {
        item.setCarrinho(this);
        itens.add(item);
    }

    public void removerItem(CarrinhoItem item) {
        itens.remove(item);
    }

    public Double getValorTotal() {
        return itens.stream()
                .mapToDouble(CarrinhoItem::getSubtotal)
                .sum();
    }
}
