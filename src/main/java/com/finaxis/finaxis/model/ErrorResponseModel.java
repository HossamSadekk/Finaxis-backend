package com.finaxis.finaxis.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ErrorResponseModel {
    private HttpStatus status;
    private String message;
    private boolean success;
}