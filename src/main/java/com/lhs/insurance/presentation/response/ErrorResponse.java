package com.lhs.insurance.presentation.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
    private String details;
}
