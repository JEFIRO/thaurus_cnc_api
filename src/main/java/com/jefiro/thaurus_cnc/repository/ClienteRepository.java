package com.jefiro.thaurus_cnc.repository;

import com.jefiro.thaurus_cnc.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByRemoteJidAndAtivoTrue(String remoteJid);

    Page<Cliente> findByCpfAndAtivoTrue(String cpf, Pageable pageable);

    Page<Cliente> findByTelefoneAndAtivoTrue(String telefone, Pageable pageable);

    Page<Cliente> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome, Pageable pageable);

    Page<Cliente> findAllByAtivoTrue(Pageable pageable);
}
