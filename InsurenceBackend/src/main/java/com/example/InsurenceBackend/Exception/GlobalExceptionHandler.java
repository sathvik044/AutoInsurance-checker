package com.example.InsurenceBackend.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
@lombok.extern.slf4j.Slf4j
public class GlobalExceptionHandler {

    // CLAIM NOT FOUND
    @ExceptionHandler(ClaimNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleClaimNotFound(
            ClaimNotFoundException ex,
            WebRequest request) {
        log.error("Claim not found: {}", ex.getMessage());
        return buildResponse(ex.getMessage(), 404, request.getContextPath());
    }

    // USER NOT FOUND
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(
            UserNotFoundException ex,
            WebRequest request) {
        log.error("User not found: {}", ex.getMessage());
        return buildResponse(ex.getMessage(), 404, request.getContextPath());
    }

    // POLICY NOT FOUND
    @ExceptionHandler(PolicyNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePolicyNotFound(
            PolicyNotFoundException ex,
            WebRequest request) {
        log.error("Policy not found: {}", ex.getMessage());
        return buildResponse(ex.getMessage(), 404, request.getContextPath());
    }

    // CLAIM DOCUMENT NOT FOUND
    @ExceptionHandler(ClaimDocNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleClaimDocNotFound(
            ClaimDocNotFoundException ex,
            WebRequest request) {
        log.error("Claim document not found: {}", ex.getMessage());
        return buildResponse(ex.getMessage(), 404, request.getContextPath());
    }

    // BAD REQUEST
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(
            BadRequestException ex,
            WebRequest request) {
        log.error("Bad request: {}", ex.getMessage());
        return buildResponse(ex.getMessage(), 400, request.getContextPath());
    }

    // ALL OTHER ERRORS
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobal(
            Exception ex,
            WebRequest request) {
        log.error("Unhandled exception: ", ex);
        String path = request.getDescription(false).replace("uri=", "");
        return buildResponse("Internal Server Error", 500, path);
    }

    // COMMON METHOD
    private ResponseEntity<ErrorResponse> buildResponse(
            String message, int status, String path) {

        ErrorResponse error = ErrorResponse.builder()
                .message(message)
                .status(status)
                .timestamp(LocalDateTime.now())
                .path(path)
                .build();

        return new ResponseEntity<>(error, HttpStatus.valueOf(status));
    }
}
