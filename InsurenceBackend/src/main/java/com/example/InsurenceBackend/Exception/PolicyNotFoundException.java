package com.example.InsurenceBackend.Exception;

public class PolicyNotFoundException extends RuntimeException {
    public PolicyNotFoundException(String message) {
        super(message);
    }
}
