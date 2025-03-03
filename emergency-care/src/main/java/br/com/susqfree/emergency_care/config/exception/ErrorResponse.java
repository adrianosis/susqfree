package br.com.susqfree.emergency_care.config.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        String message,
        List<String> details,
        LocalDateTime timestamp
) {
}
