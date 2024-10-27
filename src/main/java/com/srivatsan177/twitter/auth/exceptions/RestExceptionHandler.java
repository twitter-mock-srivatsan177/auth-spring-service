package com.srivatsan177.twitter.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.srivatsan177.twitter.auth.models.dto.ErrorResponseDTO;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ErrorResponseDTO> handleAuthorizationException(AuthorizationException e) {
        ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .status(false)
                .message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(RestException.class)
    public ResponseEntity<ErrorResponseDTO> handleRestException(RestException e) {
        ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .status(false)
                .message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(BadRequestRestException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadRequestRestException(BadRequestRestException e) {
        ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .status(false)
                .message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
