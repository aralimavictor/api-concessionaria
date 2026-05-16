package org.com.serratec.concessionaria.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
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

    @Min(value = 1900, message = "Ano deve ser a partir de 1900")
    @NotNull(message = "Ano é obrigatório")
    private Integer ano;

    @DecimalMin(value = "1.0", message = "Valor deve ser no mínimo 1")
    @NotNull(message = "Valor é obrigatório")
    private BigDecimal valor;

    @NotBlank(message = "Placa é obrigatória")
    private String placa;

    @DecimalMin(value = "0.0", message = "Desconto minimo de 0")
    @NotNull(message = "Desconto máximo é obrigatório")
    private BigDecimal maximoDesconto;

    private Boolean vendido = false;

    private BigDecimal valorVenda;

}
