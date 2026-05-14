package org.com.serratec.concessionaria.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoRequest {

    @NotNull(message = "Cliente é obrigatório")
    private UUID clienteId;

    @NotBlank(message = "Marca é obrigatória")
    private String marca;

    @NotBlank(message = "Modelo é obrigatório")
    private String modelo;

    @NotNull(message = "Ano é obrigatório")
    private Integer ano;

    @NotNull(message = "Valor é obrigatório")
    private BigDecimal valor;

    @NotBlank(message = "Placa é obrigatória")
    private String placa;

    @NotNull(message = "Desconto máximo é obrigatório")
    private BigDecimal maximoDesconto;

    private Boolean vendido = false;

    private BigDecimal valorVenda;

}
