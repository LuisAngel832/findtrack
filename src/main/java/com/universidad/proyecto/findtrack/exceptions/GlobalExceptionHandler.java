package com.universidad.proyecto.findtrack.exceptions;

import org.springframework.security.access.AccessDeniedException;
import java.time.Instant;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.universidad.proyecto.findtrack.dto.response.ExceptionResponseDTO;
import com.universidad.proyecto.findtrack.dto.response.FieldErrorDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildExceptionResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<FieldErrorDTO> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
            .map(error -> new FieldErrorDTO(error.getField(), error.getDefaultMessage()))
            .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildExceptionResponse(HttpStatus.BAD_REQUEST, "Datos de entrada inválidos", fieldErrors));
    }
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponseDTO> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(buildExceptionResponse(HttpStatus.CONFLICT, "Violación de integridad de datos"));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionResponseDTO> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildExceptionResponse(HttpStatus.BAD_REQUEST, "Tipo de argumento inválido " + ex.getName() + ": " + ex.getValue()));
    }

    @ExceptionHandler(CategoryInUseException.class)
    public ResponseEntity<ExceptionResponseDTO> handleCategoryInUseException(CategoryInUseException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(buildExceptionResponse(HttpStatus.CONFLICT, ex.getMessage()));
    }
 
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponseDTO> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(buildExceptionResponse(HttpStatus.FORBIDDEN, "Acceso denegado"));
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponseDTO> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(buildExceptionResponse(HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ExceptionResponseDTO> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(buildExceptionResponse(HttpStatus.UNAUTHORIZED, ex.getMessage()));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ExceptionResponseDTO> handleInvalidTokenException(InvalidTokenException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(buildExceptionResponse(HttpStatus.UNAUTHORIZED, ex.getMessage()));
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ExceptionResponseDTO> handleTokenExpiredException(TokenExpiredException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(buildExceptionResponse(HttpStatus.UNAUTHORIZED, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDTO> handleGenericException(Exception ex) {
        ex.printStackTrace(); // Log the exception for debugging purposes
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error inesperado"));
    }



    private ExceptionResponseDTO buildExceptionResponse(HttpStatus status, String message) {
        return new ExceptionResponseDTO(
            status.value(),
            status.getReasonPhrase(),
            message,
            Instant.now()
        );
    }

     private ExceptionResponseDTO buildExceptionResponse(HttpStatus status, String message, List<FieldErrorDTO> errors) {
        return new ExceptionResponseDTO(
            status.value(),
            status.getReasonPhrase(),
            message,
            Instant.now(),
            errors
        );
    }
}


