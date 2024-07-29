package com.finaxis.finaxis.mapper;

import com.finaxis.finaxis.entity.User;
import com.finaxis.finaxis.model.authentication.RegisterRequestModel;
import com.finaxis.finaxis.security.services.EncryptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    EncryptionService encryptionService;

    public User toUser(RegisterRequestModel requestModel) {
        return User
                .builder()
                .username(requestModel.getUsername())
                .phoneNumber(requestModel.getPhone())
                .passcode(encryptionService.hashPasscode(requestModel.getPasscode()))
                .build();
    }
}
