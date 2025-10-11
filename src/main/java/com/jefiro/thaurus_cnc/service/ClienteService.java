package com.jefiro.thaurus_cnc.service;

import com.jefiro.thaurus_cnc.dto.ClienteDTO;
import com.jefiro.thaurus_cnc.model.Cliente;
import com.jefiro.thaurus_cnc.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;

    public Cliente findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Cliente nao encontrado"));
    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Cliente findByTelefone(String telefone) {
        return repository.findByTelefone(telefone);
    }

    public Cliente novo(ClienteDTO clienteDTO) {
        return repository.save(new Cliente(clienteDTO));
    }


}
