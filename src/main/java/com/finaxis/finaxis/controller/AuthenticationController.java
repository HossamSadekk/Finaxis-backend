package com.finaxis.finaxis.controller;

import com.finaxis.finaxis.model.ResponseModel;
import com.finaxis.finaxis.model.authentication.AuthenticationResponseModel;
import com.finaxis.finaxis.model.authentication.LoginRequestModel;
import com.finaxis.finaxis.model.authentication.RegisterRequestModel;
import com.finaxis.finaxis.security.services.JWTService;
import com.finaxis.finaxis.security.services.TokenBlacklistService;
import com.finaxis.finaxis.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    JWTService jwtService;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;


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

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        String token = jwtService.getTokenFromRequest(request);
        if (token != null) {
            tokenBlacklistService.blacklistToken(token);
        }
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
