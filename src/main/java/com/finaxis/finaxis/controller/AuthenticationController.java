package com.finaxis.finaxis.controller;

import com.finaxis.finaxis.model.ResponseModel;
import com.finaxis.finaxis.model.authentication.AuthenticationResponseModel;
import com.finaxis.finaxis.model.authentication.LoginRequestModel;
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

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseModel> register(@Valid @RequestBody RegisterRequestModel requestModel) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authenticationService.register(requestModel));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseModel> login(@Valid @RequestBody LoginRequestModel request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authenticationService.login(request));
    }
}
