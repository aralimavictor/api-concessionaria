package org.com.serratec.concessionaria.service;

import lombok.RequiredArgsConstructor;
import org.com.serratec.concessionaria.entity.Cliente;
import org.com.serratec.concessionaria.entity.Veiculo;
import org.com.serratec.concessionaria.exception.BadRequestException;
import org.com.serratec.concessionaria.exception.ConflictException;
import org.com.serratec.concessionaria.exception.ResourceNotFoundException;
import org.com.serratec.concessionaria.model.VeiculoRequest;
import org.com.serratec.concessionaria.model.VeiculoResponse;
import org.com.serratec.concessionaria.model.VeiculoUpdateRequest;
import org.com.serratec.concessionaria.repository.ClienteRepository;
import org.com.serratec.concessionaria.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public VeiculoResponse cadastrar(VeiculoRequest request) {
        if (veiculoRepository.existsByPlaca(request.getPlaca())) {
            throw new ConflictException("Placa já cadastrada: " + request.getPlaca());
        }

        if (Boolean.TRUE.equals(request.getVendido()) && request.getValorVenda() == null) {
            throw new BadRequestException("valorVenda é obrigatório quando o veículo está vendido");
        }

        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + request.getClienteId()));

        Veiculo veiculo = new Veiculo();
        veiculo.setCliente(cliente);
        veiculo.setMarca(request.getMarca());
        veiculo.setModelo(request.getModelo());
        veiculo.setAno(request.getAno());
        veiculo.setValor(request.getValor());
        veiculo.setPlaca(request.getPlaca());
        veiculo.setMaximoDesconto(request.getMaximoDesconto());
        veiculo.setVendido(request.getVendido() != null ? request.getVendido() : false);
        veiculo.setValorVenda(request.getValorVenda());

        return toResponse(veiculoRepository.save(veiculo));
    }

    public List<VeiculoResponse> listar() {
        return veiculoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<VeiculoResponse> buscar(String placa, String marca, String modelo) {
        if (placa != null) {
            Veiculo veiculo = veiculoRepository.findByPlaca(placa)
                    .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado com placa: " + placa));
            return List.of(toResponse(veiculo));
        }

        if (marca != null) {
            List<Veiculo> veiculos = veiculoRepository.findByMarca(marca);
            if (veiculos.isEmpty()) {
                throw new ResourceNotFoundException("Nenhum veículo encontrado com marca: " + marca);
            }
            return veiculos.stream().map(this::toResponse).collect(Collectors.toList());
        }

        if (modelo != null) {
            List<Veiculo> veiculos = veiculoRepository.findByModelo(modelo);
            if (veiculos.isEmpty()) {
                throw new ResourceNotFoundException("Nenhum veículo encontrado com modelo: " + modelo);
            }
            return veiculos.stream().map(this::toResponse).collect(Collectors.toList());
        }

        throw new BadRequestException("Informe placa, marca ou modelo para buscar");
    }

    public VeiculoResponse atualizar(UUID id, VeiculoUpdateRequest request) {
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado com id: " + id));

        // Considera o estado atual do banco se o campo não foi enviado
        Boolean vendidoFinal = request.getVendido() != null ? request.getVendido() : veiculo.getVendido();
        boolean semValorVenda = request.getValorVenda() == null && veiculo.getValorVenda() == null;

        if (Boolean.TRUE.equals(vendidoFinal) && semValorVenda) {
            throw new BadRequestException("valorVenda é obrigatório quando o veículo está vendido");
        }

        if (request.getMarca() != null) veiculo.setMarca(request.getMarca());
        if (request.getModelo() != null) veiculo.setModelo(request.getModelo());
        if (request.getAno() != null) veiculo.setAno(request.getAno());
        if (request.getValor() != null) veiculo.setValor(request.getValor());
        if (request.getMaximoDesconto() != null) veiculo.setMaximoDesconto(request.getMaximoDesconto());
        if (request.getVendido() != null) veiculo.setVendido(request.getVendido());
        if (request.getValorVenda() != null) veiculo.setValorVenda(request.getValorVenda());

        return toResponse(veiculoRepository.save(veiculo));
    }

    public void remover(UUID id) {
        if (!veiculoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Veículo não encontrado com id: " + id);
        }
        veiculoRepository.deleteById(id);
    }

    private VeiculoResponse toResponse(Veiculo veiculo) {
        return new VeiculoResponse(
                veiculo.getId(),
                veiculo.getCliente().getNome(),
                veiculo.getMarca(),
                veiculo.getModelo(),
                veiculo.getAno(),
                veiculo.getValor(),
                veiculo.getPlaca(),
                veiculo.getMaximoDesconto(),
                veiculo.getVendido(),
                veiculo.getValorVenda()
        );
    }
}