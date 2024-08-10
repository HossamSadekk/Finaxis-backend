package com.finaxis.finaxis.exception;

import com.finaxis.finaxis.model.ErrorResponseModel;
import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

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

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ResponseEntity<ErrorResponseModel> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorResponseModel errorResponse = ErrorResponseModel.builder()
                .status(HttpStatus.FORBIDDEN)
                .message("You do not have permission to access this resource.")
                .success(false)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AccountAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseModel> handleAccountAlreadyExists(AccountAlreadyExistsException ex) {
        ErrorResponseModel errorResponse = new ErrorResponseModel(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                false
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseModel> handleUsernameAlreadyExists(UsernameAlreadyExistsException ex) {
        ErrorResponseModel errorResponse = new ErrorResponseModel(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                false
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<String> handleInsufficientFunds(InsufficientFundsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}