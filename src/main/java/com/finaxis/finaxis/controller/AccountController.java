package com.finaxis.finaxis.controller;

import com.finaxis.finaxis.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/check-username")
    public ResponseEntity<Boolean> isUsernameTaken(@RequestParam String username) {
        boolean isTaken = accountService.isUsernameTaken(username);
        return ResponseEntity.ok(isTaken);
    }
}
