package com.finaxis.finaxis.security.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {
    @Value("${encryption.salt.rounds}")
    private int saltRounds;
    private String salt;

    @PostConstruct
    public void PostConstruct() {
        salt = BCrypt.gensalt(saltRounds);
    }

    public String hashPasscode(String plainPasscode) {
        return BCrypt.hashpw(plainPasscode, salt);
    }

    public boolean checkPasscode(String plainPasscode, String hashedPasscode) {
        return BCrypt.checkpw(plainPasscode, hashedPasscode);
    }
}

