package dev.antonio3a.worldapi.infra.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            NoSuchElementException.class,
            NoHandlerFoundException.class,
    })
    public ResponseEntity<ErrorResponse> handleNotFoundException(Exception ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND, List.of(ex.getMessage()), request.getDescription(false)));
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            IllegalArgumentException.class,
            IllegalStateException.class,
            PropertyReferenceException.class,
            DataIntegrityViolationException.class,
    })
    public ResponseEntity<ErrorResponse> handleBadRequestException(Exception ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST, List.of(ex.getMessage()), request.getDescription(false)));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST,
                        ex.getFieldErrors()
                                .stream()
                                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                                .toList(),
                        request.getDescription(false))
                );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(HttpStatus.UNAUTHORIZED, List.of(ex.getMessage()), request.getDescription(false)));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedExceptionException(AccessDeniedException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(HttpStatus.FORBIDDEN, List.of(ex.getMessage()), request.getDescription(false)));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, List.of(ex.getMessage()), request.getDescription(false)));
    }
}
