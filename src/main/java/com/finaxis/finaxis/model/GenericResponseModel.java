package com.finaxis.finaxis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class GenericResponseModel {
    private HttpStatus status;
    private String message;
}
