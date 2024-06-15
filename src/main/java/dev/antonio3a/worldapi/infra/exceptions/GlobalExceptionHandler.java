package dev.antonio3a.worldapi.infra.exceptions;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.method.ParameterErrors;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({
            NoSuchElementException.class,
            NoHandlerFoundException.class,
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(Exception ex, WebRequest request) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, List.of(ex.getMessage()), request.getDescription(false));
    }

    @ExceptionHandler({
            DataIntegrityViolationException.class,
            HandlerMethodValidationException.class,
            HttpMessageNotReadableException.class,
            HttpRequestMethodNotSupportedException.class,
            IllegalArgumentException.class,
            IllegalStateException.class,
            MethodArgumentNotValidException.class,
            NoResourceFoundException.class,
            PropertyReferenceException.class,
    })
    @ApiResponse(
            responseCode = "400",
            description = "Bad Request",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = ErrorResponse.class
                    )
            )
    )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(Exception ex, WebRequest request) {
        List<String> messages = switch (ex) {
            case MethodArgumentNotValidException maex -> maex.getFieldErrors()
                    .stream()
                    .map(fieldError -> "field: " + "'" + fieldError.getField() + "'" + " " + fieldError.getDefaultMessage())
                    .toList();
            case HandlerMethodValidationException hmex -> hmex.getAllValidationResults()
                    .stream()
                    .flatMap(parameterValidationResult -> {
                                if (parameterValidationResult instanceof ParameterErrors parameterErrors) {
                                    return parameterErrors.getFieldErrors()
                                            .stream()
                                            .map(fieldError -> "field: " + "'" + fieldError.getField() + "'" + " " + fieldError.getDefaultMessage());
                                } else {
                                    return parameterValidationResult.getResolvableErrors()
                                            .stream()
                                            .map(messageSourceResolvable ->
                                                    "parameter: " + "'" + parameterValidationResult.getMethodParameter()
                                                            .getParameterName() + "'" + " " + messageSourceResolvable.getDefaultMessage());
                                }
                            }
                    )
                    .toList();
            default -> List.of(ex.getMessage());
        };
        return new ErrorResponse(HttpStatus.BAD_REQUEST, messages, request.getDescription(false));
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = ErrorResponse.class
                    )
            )
    )
    public ErrorResponse handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        return new ErrorResponse(HttpStatus.UNAUTHORIZED, List.of(ex.getMessage()), request.getDescription(false));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ApiResponse(
            responseCode = "403",
            description = "Forbidden",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = ErrorResponse.class
                    )
            )
    )
    public ErrorResponse handleAccessDeniedExceptionException(AccessDeniedException ex, WebRequest request) {
        return new ErrorResponse(HttpStatus.FORBIDDEN, List.of(ex.getMessage()), request.getDescription(false));
    }

    @ExceptionHandler(Exception.class)
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = ErrorResponse.class
                    )
            )
    )
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception ex, WebRequest request) {
        LOGGER.error("Unhandled error (' - ')...", ex);
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, List.of(ex.getMessage()), request.getDescription(false));
    }
}
