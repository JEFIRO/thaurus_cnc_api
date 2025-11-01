package com.jefiro.thaurus_cnc.service;

import com.jefiro.thaurus_cnc.dto.ClienteDTO;
import com.jefiro.thaurus_cnc.infra.exception.DadosInvalidosException;
import com.jefiro.thaurus_cnc.infra.exception.RecursoNaoEncontradoException;
import com.jefiro.thaurus_cnc.model.Cliente;
import com.jefiro.thaurus_cnc.model.Endereco;
import com.jefiro.thaurus_cnc.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;
    @Autowired
    private ViaCepService viaCepService;

    public Cliente findById(Long id) {
        return repository.findById(id).orElseThrow(RecursoNaoEncontradoException::new);
    }

    public Page<Cliente> findAll(Pageable pageable) {
        return repository.findAllByAtivoTrue(pageable);
    }

    public Page<Cliente> findByNome(String nome, Integer page, Integer size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return repository.findByNomeContainingIgnoreCaseAndAtivoTrue(nome, pageable);
    }

    public Page<Cliente> findAll(Integer page, Integer size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return repository.findAllByAtivoTrue(pageable);
    }

    public Page<Cliente> findByTelefone(String telefone, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return repository.findByTelefoneAndAtivoTrue(telefone, pageable);
    }

    public Page<Cliente> findByCpf(String cpf, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return repository.findByCpfAndAtivoTrue(cpf, pageable);
    }


    public Cliente novo(ClienteDTO clienteDTO) {
        if (clienteDTO == null) {
            throw new DadosInvalidosException("Cliente não pode ser nulo");
        }

        Cliente cliente = new Cliente(clienteDTO);

        if (clienteDTO.endereco().getCep() != null) {
            cliente.setEndereco(getEndereco(cliente, clienteDTO));
        }

        return repository.save(cliente);
    }


    public Cliente update(Long id, ClienteDTO cliente) {
        if (cliente == null) {
            throw new DadosInvalidosException("Cliente nao pode ser nulo");
        }
        if (id == null) {
            throw new DadosInvalidosException("Id nao pode ser nulo");
        }
        Cliente clienteEntity = repository.findById(id).orElseThrow(() -> new RuntimeException("Cliente nao encontrado"));

        if (cliente.nome() != null) {
            clienteEntity.setNome(cliente.nome());
        }
        if (cliente.telefone() != null) {
            clienteEntity.setTelefone(cliente.telefone());
        }
        if (cliente.remoteJid() != null) {
            clienteEntity.setRemoteJid(cliente.remoteJid());
        }
        if (cliente.cpf() != null) {
            clienteEntity.setCpf(cliente.cpf());
        }
        if (cliente.email() != null) {
            clienteEntity.setEmail(cliente.email());
        }
        if (cliente.endereco().getCep() != null) {
            clienteEntity.setEndereco(getEndereco(clienteEntity, cliente));
        }

        return repository.save(clienteEntity);
    }

    public boolean delete(Long id) {
        if (id == null) {
            throw new DadosInvalidosException("Id nao pode ser nulo");
        }
        try {
            Cliente cliente = repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Cliente nao encontrado"));
            cliente.setAtivo(false);
            repository.save(cliente);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public Cliente findByRemoteJid(String rij) {
        if (rij == null) {
            throw new DadosInvalidosException("RemoteJid nao pode ser nulo");
        }

        return repository.findByRemoteJidAndAtivoTrue(rij).orElseThrow(() -> new RecursoNaoEncontradoException("Cliente nao encontrado"));
    }

    public Cliente findByRemote(String rij) {
        if (rij == null) {
            throw new DadosInvalidosException("RemoteJid nao pode ser nulo");
        }
        Optional<Cliente> cliente = repository.findByRemoteJidAndAtivoTrue(rij);
        if (cliente.isPresent()) {
            return cliente.get();
        } else return new Cliente();

    }

    private Endereco getEndereco(Cliente cliente, ClienteDTO dto) {
        if (dto.endereco().getCep() != null) {
            String cep = dto.endereco().getCep().replace("-", "").trim();

            Endereco endereco = viaCepService.gerarEndereco(cep);

            if (endereco == null || endereco.isErro()) {
                throw new DadosInvalidosException("CEP inválido: " + cep);
            }

            endereco.setNumero(dto.endereco().getNumero());
            endereco.setComplemento(dto.endereco().getComplemento());

            return endereco;
        }
        return null;
    }

}
