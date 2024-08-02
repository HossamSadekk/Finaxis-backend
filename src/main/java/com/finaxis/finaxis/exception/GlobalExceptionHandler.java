package com.finaxis.finaxis.exception;

import com.finaxis.finaxis.model.ErrorResponseModel;
import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorResponseModel> handleUserAlreadyExistsException(EntityExistsException ex) {
        ErrorResponseModel errorResponse = ErrorResponseModel.builder()
                .status(HttpStatus.CONFLICT)
                .message(ex.getMessage())
                .success(false)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseModel> handleGlobalException(Exception ex) {
        ErrorResponseModel errorResponse = ErrorResponseModel.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ex.getMessage())
                .success(false)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}