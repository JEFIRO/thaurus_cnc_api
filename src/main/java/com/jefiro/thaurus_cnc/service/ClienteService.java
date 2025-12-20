package com.jefiro.thaurus_cnc.service;

import com.jefiro.thaurus_cnc.dto.cliente.ClienteResponse;
import com.jefiro.thaurus_cnc.dto.cliente.ClienteUpdate;
import com.jefiro.thaurus_cnc.dto.cliente.NewCliente;
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
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private ViaCepService viaCepService;

    private final WebClient webClient;

    public ClienteService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://thaurus-cnc-n8n.1o2rzn.easypanel.host/webhook/clientes").build();
    }


    public ClienteResponse findById(Long id) {
        Cliente cliente = repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Cliente nao encontrado"));
        return new ClienteResponse(cliente);
    }

    public Page<ClienteResponse> findByNome(String nome, Integer page, Integer size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return repository.findByNomeContainingIgnoreCaseAndAtivoTrue(nome, pageable);
    }

    public List<ClienteResponse> findAll() {
        return repository.findAllByAtivoTrue().stream().map(ClienteResponse::new).toList();
    }

    public Page<ClienteResponse> findByTelefone(String telefone, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return repository.findByTelefoneAndAtivoTrue(telefone, pageable);
    }

    public Page<ClienteResponse> findByCpf(String cpf, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return repository.findByCpfAndAtivoTrue(cpf, pageable);
    }

    public ClienteResponse novo(NewCliente clienteDTO) {
        if (clienteDTO == null) {
            throw new DadosInvalidosException("Cliente não pode ser nulo");
        }

        Cliente cliente = new Cliente(clienteDTO);

        if (clienteDTO.endereco() != null) {
            if (clienteDTO.endereco().getCep() != null) {
                cliente.setEndereco(getEndereco(clienteDTO));
            }
        }

        return new ClienteResponse(repository.save(cliente));
    }

    public ClienteResponse update(Long id, ClienteUpdate cliente) {
        if (cliente == null) {
            throw new DadosInvalidosException("Cliente nao pode ser nulo");
        }
        if (id == null) {
            throw new DadosInvalidosException("Id nao pode ser nulo");
        }
        Cliente clienteEntity = repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Cliente nao encontrado"));

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
        if (cliente.endereco() != null) {
            clienteEntity.setEndereco(getEndereco(cliente));
        }

        return new ClienteResponse(repository.save(clienteEntity));
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

    private Endereco getEndereco(NewCliente dto) {
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

    private Endereco getEndereco(ClienteUpdate dto) {
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

    public Cliente updateInterno(Long id, ClienteUpdate cliente) {
        if (cliente == null) {
            throw new DadosInvalidosException("Cliente nao pode ser nulo");
        }
        if (id == null) {
            throw new DadosInvalidosException("Id nao pode ser nulo");
        }
        Cliente clienteEntity = repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Cliente nao encontrado"));

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
        if (cliente.endereco() != null) {
            clienteEntity.setEndereco(getEndereco(cliente));
        }

        return repository.save(clienteEntity);
    }

    @Scheduled(fixedRate = 864000000L)
    public void clienteLembrete() {
        repository.buscarClientesParaLembreteNovoPedido().forEach(cliente -> {

            Map<String, Object> map = new HashMap<>();

            map.put("remoteJid", cliente.getRemoteJid());
            map.put("nome", cliente.getNome());

            enviarLembreteCliente(map).subscribe();

            cliente.setUltimo_lembrete_novo_pedido(LocalDateTime.now());
            repository.save(cliente);
            System.out.println("Lembrete enviado para " + cliente.getRemoteJid());
        });
    }

    private Mono<String> enviarLembreteCliente(Map<String, Object> map) {
        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(map)
                .retrieve()
                .bodyToMono(String.class);
    }
}
