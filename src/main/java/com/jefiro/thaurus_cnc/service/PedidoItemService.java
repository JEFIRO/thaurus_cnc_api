package com.jefiro.thaurus_cnc.service;

import com.jefiro.thaurus_cnc.infra.exception.RecursoNaoEncontradoException;
import com.jefiro.thaurus_cnc.model.PedidoItem;
import com.jefiro.thaurus_cnc.repository.PedidoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class PedidoItemService {
    @Autowired
    private PedidoItemRepository pedidoItemRepository;

    public PedidoItem buscar(Long id) {
        return pedidoItemRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("PedidoItem nao encontrado"));
    }

    public List<PedidoItem> salvar(List<PedidoItem> pedidoItem) {
        if (pedidoItem != null) {
            return pedidoItemRepository.saveAll(pedidoItem);
        }
        return null;
    }

}
