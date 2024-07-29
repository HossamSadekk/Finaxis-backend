package com.finaxis.finaxis.services.imp;

import com.finaxis.finaxis.entity.User;
import com.finaxis.finaxis.mapper.UserMapper;
import com.finaxis.finaxis.model.authentication.AuthenticationResponseModel;
import com.finaxis.finaxis.model.authentication.RegisterRequestModel;
import com.finaxis.finaxis.repository.UserRepository;
import com.finaxis.finaxis.security.services.JWTService;
import com.finaxis.finaxis.services.AuthenticationService;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JWTService jwtService;
    @Autowired
    UserMapper userMapper;


    @Override
    public AuthenticationResponseModel register(RegisterRequestModel request) {
        if (isUsernameOrPhoneNumberExist(request.getUsername(), request.getPhone())) {
            throw new EntityExistsException("username or phone number already exist");
        }
        User user = userRepository.save(userMapper.toUser(request));
        return AuthenticationResponseModel.builder().token(jwtService.generateJWT(user)).build();
    }

    private Boolean isUsernameOrPhoneNumberExist(String username, String phoneNumber) {
        return userRepository.existsByUsernameAndPhoneNumber(username, phoneNumber);
    }
}
