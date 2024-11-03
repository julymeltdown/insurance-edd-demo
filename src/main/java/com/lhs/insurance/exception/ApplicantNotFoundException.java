package com.lhs.insurance.exception;

import jakarta.persistence.EntityNotFoundException;

public class ApplicantNotFoundException extends EntityNotFoundException { // 계약자 못찾는 경우 발생하는 exception
    public ApplicantNotFoundException(String message) {
        super(message);
    }
}
