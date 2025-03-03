package br.com.fiap.triage_service.infra.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MessageError {

    public LocalDateTime timestamp;

    public Integer status;

    public String message;
}
