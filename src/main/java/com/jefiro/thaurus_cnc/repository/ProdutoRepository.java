package com.jefiro.thaurus_cnc.repository;

import com.jefiro.thaurus_cnc.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    @Query(value = "SELECT * FROM produto WHERE ativo = true", nativeQuery = true)
    List<Produto> findAllAtivos();

    @Query(value = "SELECT * FROM produto WHERE ativo = true AND id = :id", nativeQuery = true)
    Optional<Produto> getAllAtivos(Long id);
}
