package org.com.serratec.concessionaria.service;


import lombok.RequiredArgsConstructor;
import org.com.serratec.concessionaria.entity.Cliente;
import org.com.serratec.concessionaria.exception.ConflictException;
import org.com.serratec.concessionaria.exception.ResourceNotFoundException;
import org.com.serratec.concessionaria.model.ClienteRequest;
import org.com.serratec.concessionaria.model.ClienteResponse;
import org.com.serratec.concessionaria.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {


    private final ClienteRepository clienteRepository;

    public ClienteResponse cadastrar(ClienteRequest request){
        if(clienteRepository.existsByCpf(request.getCpf())){
            throw new ConflictException("CPF já Cadastrado!" + request.getCpf());
        }
        Cliente cliente = new Cliente();
        cliente.setNome(request.getNome());
        cliente.setTelefone(request.getTelefone());
        cliente.setCpf(request.getCpf());
        cliente.setEmail(request.getEmail());

        return toResponse(clienteRepository.save(cliente));
    }

    public List<ClienteResponse> listar(){
        return clienteRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<ClienteResponse> buscar(String nome, String cpf) {
        if (cpf != null) {
            Cliente cliente = clienteRepository.findByCpf(cpf)
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o CPF: " + cpf));
            return List.of(toResponse(cliente));
        }

        if (nome != null) {
            List<Cliente> clientes = clienteRepository.findByNomeIgnoreCaseAndAccent(nome);
            if (clientes.isEmpty()) {
                throw new ResourceNotFoundException("Nenhum cliente encontrado com o nome: " + nome);
            }
            return clientes.stream().map(this::toResponse).collect(Collectors.toList());
        }

        throw new ResourceNotFoundException("Informe o CPF ou o nome para buscar");
    }

    public void remover(UUID id ){
        if(!clienteRepository.existsById(id)){
            throw new ResourceNotFoundException("Cliente não encontrado com o id: " + id);
        }
        clienteRepository.deleteById(id);
    }

    private ClienteResponse toResponse(Cliente cliente){
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNome(),
                cliente.getTelefone(),
                cliente.getCpf(),
                cliente.getEmail()
        );
    }
}
