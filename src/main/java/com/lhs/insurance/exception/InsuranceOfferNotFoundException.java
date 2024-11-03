package com.lhs.insurance.exception;

public class InsuranceOfferNotFoundException extends RuntimeException {
    public InsuranceOfferNotFoundException(String message) {
        super(message);
    }
}
