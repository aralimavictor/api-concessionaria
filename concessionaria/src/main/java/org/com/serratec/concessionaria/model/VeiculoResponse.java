package org.com.serratec.concessionaria.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class VeiculoResponse {

    private UUID id;
    private String clienteNome;
    private String marca;
    private String modelo;
    private Integer ano;
    private BigDecimal valor;
    private String placa;
    private BigDecimal maximoDesconto;
    private boolean vendido;
    private BigDecimal valorVenda;
}
