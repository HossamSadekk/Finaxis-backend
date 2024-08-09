package com.finaxis.finaxis.controller;

import com.finaxis.finaxis.entity.Account;
import com.finaxis.finaxis.entity.User;
import com.finaxis.finaxis.model.user.AccountResponseModel;
import com.finaxis.finaxis.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/me")
    public ResponseEntity<AccountResponseModel> getLoggedInUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.getFirstAccountOfUser(user.getId()));
    }
}
