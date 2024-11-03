package com.lhs.insurance.exception;

import jakarta.persistence.EntityNotFoundException;

public class InsuranceAgentNotFoundException extends EntityNotFoundException {
    public InsuranceAgentNotFoundException(String message) {
        super(message);
    }
}
