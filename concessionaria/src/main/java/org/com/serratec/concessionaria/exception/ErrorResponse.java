package org.com.serratec.concessionaria.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ErrorResponse {
    private int status;
    private String mensagem;
    private LocalDateTime timestamp;

    public ErrorResponse(int status, String mensagem) {
        this.status = status;
        this.mensagem = mensagem;
        this.timestamp = LocalDateTime.now();
    }
}