package com.jefiro.thaurus_cnc.repository;

import com.jefiro.thaurus_cnc.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {
    @Query(value = "SELECT * FROM pedido WHERE ativo = true",nativeQuery = true)
    List<Pedido> findAllAtivos();
    @Query(value = "SELECT * FROM pedido WHERE ativo = true AND cliente_id = :id",nativeQuery = true)
    Optional<Pedido> findByCliente_Id(Long id);
    @Query(value = "SELECT * FROM pedido WHERE ativo = true AND id = :id",nativeQuery = true)
    Optional<Pedido> getAtivos(Long id);
}
