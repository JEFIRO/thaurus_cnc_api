package com.jefiro.thaurus_cnc.repository;

import com.jefiro.thaurus_cnc.dto.pedido.PedidoCardView;
import com.jefiro.thaurus_cnc.model.Pedido;
import com.jefiro.thaurus_cnc.model.StatusPedido;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    @Query(value = "SELECT * FROM pedido WHERE ativo = true", nativeQuery = true)
    List<Pedido> findAllAtivos();

    @Query(value = "SELECT * FROM pedido WHERE ativo = true AND cliente_id = :id", nativeQuery = true)
    List<Pedido> findByClienteId(@Param("id") Long id);

    @Query(value = "SELECT * FROM pedido WHERE ativo = true AND id = :id", nativeQuery = true)
    Optional<Pedido> getAtivos(Long id);

    @Query(value = "SELECT * FROM pedido WHERE ativo = true AND id_pedido = :uuid", nativeQuery = true)
    Optional<Pedido> findById_Pedido(@Param("uuid") String uuid);

    @Modifying
    @Transactional
    @Query(
            value = """
        UPDATE pedido
        SET status = 'CANCLED'
        WHERE data_pedido < (CURRENT_TIMESTAMP AT TIME ZONE 'America/Sao_Paulo') - INTERVAL '30 days'
          AND status = 'LAYOUT_PENDING'
          AND ativo = true
        """,
            nativeQuery = true
    )
    int clearPedidos();


    @Query(value = """
            SELECT
                p.id AS pedidoId,
                p.id_pedido AS idPedido,
            
                c.id AS clienteId,
                c.nome AS clienteNome,
                c.telefone AS clienteTelefone,
                c.email AS clienteEmail,
            
                COUNT(DISTINCT i.id_item) AS quantidadeItens,
            
                pr.nome AS produtoNome,
                pr.imagem AS imagem,
            
                p.valor_total AS valorTotal,
                p.status AS status,
                p.data_pedido AS dataPedido,
                p.ativo AS ativo,
                CONCAT('/api/pedidos/', p.id) AS linkDetalhes
            
            FROM pedido p
            JOIN cliente c ON c.id = p.cliente_id
            JOIN pedido_item i ON i.pedido_id = p.id
            JOIN produto pr ON pr.id = i.produto_id
            GROUP BY
                p.id,
                p.id_pedido,
                c.id,
                c.nome,
                c.telefone,
                c.email,
                pr.nome,
                pr.imagem,
                p.valor_total,
                p.status,
                p.data_pedido,
                p.ativo
            """, nativeQuery = true)
    Page<PedidoCardView> listarCards(Pageable pageable);

    @Query("""
            SELECT p 
            FROM Pedido p 
            WHERE p.status NOT IN (com.jefiro.thaurus_cnc.model.StatusPedido.DELIVERED,
                                   com.jefiro.thaurus_cnc.model.StatusPedido.CANCLED)
              AND p.ativo = true
            """)
    List<Pedido> pedidosEmAberto();

    @Query("select p.status from Pedido p where p.id = :id")
    StatusPedido getStatusPedido(Long id);

}
