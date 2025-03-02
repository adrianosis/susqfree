package br.com.susqfree.schedule_management.infra.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageError {

    public LocalDateTime timestamp;

    public Integer status;

    public String message;

}
