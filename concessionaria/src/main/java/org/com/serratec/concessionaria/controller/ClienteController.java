package org.com.serratec.concessionaria.controller;

import lombok.RequiredArgsConstructor;
import org.com.serratec.concessionaria.model.ClienteRequest;
import org.com.serratec.concessionaria.model.ClienteResponse;
import org.com.serratec.concessionaria.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cliente")
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponse> cadastrar(@RequestBody @Valid ClienteRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.cadastrar(request));
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> listarOuBuscar(
        @RequestParam(required = false) String nome,
        @RequestParam(required = false) String cpf){

        if(nome != null || cpf != null){
            return ResponseEntity.ok(clienteService.buscar(nome,cpf));
        }
        return ResponseEntity.ok(clienteService.listar());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable UUID id){
        clienteService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
