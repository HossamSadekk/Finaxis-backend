package com.finaxis.finaxis.controller;

import com.finaxis.finaxis.model.ResponseModel;
import com.finaxis.finaxis.model.authentication.RegisterRequestModel;
import com.finaxis.finaxis.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity<ResponseModel> register(@Valid @RequestBody RegisterRequestModel requestModel) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .success(true)
                        .data(authenticationService.register(requestModel))
                        .build());
    }
}
