package org.com.serratec.concessionaria.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.com.serratec.concessionaria.model.VeiculoRequest;
import org.com.serratec.concessionaria.model.VeiculoResponse;
import org.com.serratec.concessionaria.model.VeiculoUpdateRequest;
import org.com.serratec.concessionaria.service.VeiculoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/veiculo")
public class VeiculoController {

    private final VeiculoService veiculoService;

    @PostMapping
    public ResponseEntity<VeiculoResponse> cadastrar(@RequestBody @Valid VeiculoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(veiculoService.cadastrar(request));
    }

    @GetMapping
    public ResponseEntity<List<VeiculoResponse>> listarOuBuscar(
            @RequestParam(required = false) String placa,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String modelo) {

        if (placa != null || marca != null || modelo != null) {
            return ResponseEntity.ok(veiculoService.buscar(placa, marca, modelo));
        }
        return ResponseEntity.ok(veiculoService.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeiculoResponse> atualizar(
            @PathVariable UUID id,
            @RequestBody VeiculoUpdateRequest request) {
        return ResponseEntity.ok(veiculoService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        veiculoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
