package com.sec.security.service;

import com.sec.security.model.dto.requests.AuthenticateRequest;
import com.sec.security.model.dto.requests.RegisterRequest;
import com.sec.security.model.dto.response.AuthenticationResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

    public AuthenticationResponse register(RegisterRequest request){

        return null;
    }
    public AuthenticationResponse authenticate(AuthenticateRequest request){

        return null;
    }

}
