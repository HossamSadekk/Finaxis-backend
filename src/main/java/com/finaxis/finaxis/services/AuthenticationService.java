package com.finaxis.finaxis.services;

import com.finaxis.finaxis.model.authentication.AuthenticationResponseModel;
import com.finaxis.finaxis.model.authentication.RegisterRequestModel;

public interface AuthenticationService {
    AuthenticationResponseModel register(RegisterRequestModel request);
}
