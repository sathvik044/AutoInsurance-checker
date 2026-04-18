package com.example.InsurenceBackend.Exception;

public class ClaimTransactionNotFoundException extends RuntimeException {
    public ClaimTransactionNotFoundException(String message) {
        super(message);
    }
}
