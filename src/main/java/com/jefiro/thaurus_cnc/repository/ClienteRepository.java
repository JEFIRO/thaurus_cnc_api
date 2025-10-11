package com.jefiro.thaurus_cnc.repository;

import com.jefiro.thaurus_cnc.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {
    Cliente findByTelefone(String telefone);
}
