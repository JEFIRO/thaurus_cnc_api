package com.jefiro.thaurus_cnc.dto.pedido.item;

public record ItemCard(
        Long id,
        String nome_produto,
        String img,
        Double valor) {
    public ItemCard(Long id, String nome_produto, String img, Double valor) {
        this.id = id;
        this.nome_produto = nome_produto;
        this.img = img;
        this.valor = valor;
    }
}
