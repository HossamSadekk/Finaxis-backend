package com.finaxis.finaxis.services.imp;

import com.finaxis.finaxis.entity.User;
import com.finaxis.finaxis.mapper.UserMapper;
import com.finaxis.finaxis.model.authentication.AuthenticationResponseModel;
import com.finaxis.finaxis.model.authentication.LoginRequestModel;
import com.finaxis.finaxis.model.authentication.RegisterRequestModel;
import com.finaxis.finaxis.repository.UserRepository;
import com.finaxis.finaxis.security.services.JWTService;
import com.finaxis.finaxis.services.AuthenticationService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JWTService jwtService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    AuthenticationManager authenticationManager;


    @Override
    public AuthenticationResponseModel register(RegisterRequestModel request) {
        if (isUsernameOrPhoneNumberExist(request.getUsername(), request.getPhone())) {
            throw new EntityExistsException("username or phone number already exist");
        }
        User user = userRepository.save(userMapper.toUser(request));
        return AuthenticationResponseModel.builder().token(jwtService.generateJWT(user)).build();
    }

    @Override
    public AuthenticationResponseModel login(LoginRequestModel request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getPhone(),
                        request.getPasscode()
                )
        );

        var user = userRepository.findByPhoneNumber(request.getPhone())
                .orElseThrow(() -> new EntityNotFoundException("User phone :" + request.getPhone() + " Not Found"));
        System.out.println(user.getUsername());
        return AuthenticationResponseModel
                .builder()
                .token(jwtService.generateJWT(user))
                .build();
    }

    private Boolean isUsernameOrPhoneNumberExist(String username, String phoneNumber) {
        return userRepository.existsByUsernameAndPhoneNumber(username, phoneNumber);
    }
}
