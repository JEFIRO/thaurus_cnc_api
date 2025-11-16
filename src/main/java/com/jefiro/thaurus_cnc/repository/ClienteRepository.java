package com.jefiro.thaurus_cnc.repository;

import com.jefiro.thaurus_cnc.dto.cliente.ClienteResponse;
import com.jefiro.thaurus_cnc.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByRemoteJidAndAtivoTrue(String remoteJid);

    Page<ClienteResponse> findByCpfAndAtivoTrue(String cpf, Pageable pageable);

    Page<ClienteResponse> findByTelefoneAndAtivoTrue(String telefone, Pageable pageable);

    Page<ClienteResponse> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome, Pageable pageable);

    List<Cliente> findAllByAtivoTrue();

    @Query(value = """
                SELECT c.*
                FROM cliente c
                LEFT JOIN pedido p
                    ON p.cliente_id = c.id
                    AND p.data_pedido >= (CURRENT_TIMESTAMP AT TIME ZONE 'America/Sao_Paulo') - INTERVAL '3 months'
                WHERE p.id IS NULL
                  AND (
                        c.ultimo_lembrete_novo_pedido IS NULL
                     OR c.ultimo_lembrete_novo_pedido < (CURRENT_TIMESTAMP AT TIME ZONE 'America/Sao_Paulo') - INTERVAL '60 days'
                  )
            """, nativeQuery = true)
    List<Cliente> buscarClientesParaLembreteNovoPedido();


}
