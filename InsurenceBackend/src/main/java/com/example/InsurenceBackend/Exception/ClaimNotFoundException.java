package com.example.InsurenceBackend.Exception;

public class ClaimNotFoundException extends RuntimeException {
    public ClaimNotFoundException(String message) {
        super(message);
    }
}
