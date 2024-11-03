package com.lhs.insurance.exception;

import jakarta.persistence.EntityNotFoundException;

public class InsuredPersonNotFoundException extends EntityNotFoundException {
    public InsuredPersonNotFoundException(String message) {
        super(message);
    }
}