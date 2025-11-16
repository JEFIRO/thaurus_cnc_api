package com.jefiro.thaurus_cnc.service;

import com.jefiro.thaurus_cnc.dto.ProdutoDTO;
import com.jefiro.thaurus_cnc.dto.ProdutoResponse;
import com.jefiro.thaurus_cnc.model.Produto;
import com.jefiro.thaurus_cnc.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository repository;


    public Produto novo(ProdutoDTO dto) {
        Produto produto = new Produto(dto);
        return repository.save(produto);
    }

    public List<Produto> novo(List<ProdutoDTO> dtoList) {
        List<Produto> produtos = dtoList.stream()
                .map(Produto::new)
                .collect(Collectors.toList());
        return repository.saveAll(produtos);
    }


    public List<ProdutoResponse> listar() {
        return repository.findAllAtivos().stream().map(ProdutoResponse::new).toList();
    }

    public Boolean delete(Long id) {
        try {
            Produto produto = repository.findById(id).orElseThrow(() -> new RuntimeException("Produto nao encontrado"));
            produto.setAtivo(false);
            repository.save(produto);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Produto update(Long id, ProdutoDTO dto) {
        if (id == null || dto == null) {
            throw new IllegalArgumentException("Id e dto devem ser informados");
        }

        Produto produto = repository.findById(id).orElseThrow(() -> new RuntimeException("Produto nao encontrado"));
        if (dto.nome() != null) {
            produto.setNome(dto.nome());
        }
        if (dto.imagem() != null) {
            produto.setImagem(dto.imagem());
        }
        if (dto.descricao() != null) {
            produto.setDescricao(dto.descricao());
        }
        if (dto.personalizacao() != null) {
            produto.setPersonalizacao(dto.personalizacao());
        }
        if (dto.variantes() != null) {
            produto.setVariantes(dto.variantes());
        }
        return repository.save(produto);
    }

    public Produto get(Long id) {
        return repository.getAllAtivos(id).orElseThrow(() -> new RuntimeException("Produto nao encontrado"));
    }

    public Boolean delete() {
        try {
            repository.deleteAll();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
