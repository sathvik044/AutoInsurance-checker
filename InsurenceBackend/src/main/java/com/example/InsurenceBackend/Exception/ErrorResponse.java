package com.example.InsurenceBackend.Exception;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private String message;
    private int status;
    private LocalDateTime timestamp;
    private String path;
}
