package org.com.serratec.concessionaria.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoUpdateRequest {

    private String marca;
    private String modelo;

    @Min(value = 1900, message = "Ano deve ser maior ou igual a 1900")
    private Integer ano;

    @DecimalMin(value = "1.0", message = "Valor deve ser maior ou igual a 1")
    private BigDecimal valor;

    @DecimalMin(value = "0.0", message = "Máximo desconto deve ser maior ou igual a 0")
    private BigDecimal maximoDesconto;

    private Boolean vendido;

    private BigDecimal valorVenda;
}
