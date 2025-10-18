package com.jefiro.thaurus_cnc.service;

import com.jefiro.thaurus_cnc.dto.ClienteDTO;
import com.jefiro.thaurus_cnc.model.Cliente;
import com.jefiro.thaurus_cnc.repository.ClienteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Beans;
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

    public Cliente findByCpf(String cpf) {
        return repository.findByCpf(cpf).orElseThrow(() -> new RuntimeException("Cliente nao encontrado"));
    }

    public Cliente novo(ClienteDTO clienteDTO) {
        if (clienteDTO == null) {
            throw new IllegalArgumentException("Cliente nao pode ser nulo");
        }
        return repository.save(new Cliente(clienteDTO));
    }

    public Cliente update(Long id, ClienteDTO cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente nao pode ser nulo");
        }
        if (id == null) {
            throw new IllegalArgumentException("Id nao pode ser nulo");
        }
        Cliente clienteEntity = repository.findById(id).orElseThrow(() -> new RuntimeException("Cliente nao encontrado"));

        if (cliente.nome() != null) {
            clienteEntity.setNome(cliente.nome());
        }
        if (cliente.telefone() != null) {
            clienteEntity.setTelefone(cliente.telefone());
        }
        if (cliente.cpf() != null) {
            clienteEntity.setCpf(cliente.cpf());
        }
        if (cliente.email() != null) {
            clienteEntity.setEmail(cliente.email());
        }
        if (cliente.endereco() != null) {
            clienteEntity.setEndereco(cliente.endereco());
        }

        return repository.save(clienteEntity);
    }

    public boolean delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id nao pode ser nulo");
        }
        try {
            Cliente cliente = repository.findById(id).orElseThrow(() -> new RuntimeException("Cliente nao encontrado"));
            cliente.setAtivo(false);
            repository.save(cliente);
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
