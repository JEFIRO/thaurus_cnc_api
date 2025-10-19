package com.jefiro.thaurus_cnc.dto;

public record InfinitypayItens(
        Integer quantity,
        Double price,
        String description
) {
    public InfinitypayItens(Integer quantity, Double price, String description) {
        this.quantity = quantity;
        this.price = price;
        this.description = description;
    }
}
