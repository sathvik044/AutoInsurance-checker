package com.example.InsurenceBackend.Exception;

public class ClaimDocNotFoundException extends RuntimeException {
    public ClaimDocNotFoundException(String message){
            super(message);
    }
}
