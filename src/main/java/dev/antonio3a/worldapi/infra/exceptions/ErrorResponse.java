package dev.antonio3a.worldapi.infra.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ErrorResponse {

    private LocalDateTime timestamp;

    private int status;

    private String error;

    private List<String> messages;

    private String path;

    public ErrorResponse(HttpStatus status, List<String> messages, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.error = status.toString();
        this.messages = messages;
        this.path = path;
    }
}
