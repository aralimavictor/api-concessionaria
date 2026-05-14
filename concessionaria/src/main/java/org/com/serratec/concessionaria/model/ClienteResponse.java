package org.com.serratec.concessionaria.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ClienteResponse {
    private UUID id;
    private String nome;
    private String telefone;
    private String cpf;
    private String email;
}
