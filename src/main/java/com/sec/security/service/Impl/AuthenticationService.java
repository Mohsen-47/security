package com.sec.security.service.Impl;

import com.sec.security.model.Role;
import com.sec.security.model.User;
import com.sec.security.model.dto.requests.AuthenticateRequest;
import com.sec.security.model.dto.requests.RegisterRequest;
import com.sec.security.model.dto.response.AuthenticationResponse;
import com.sec.security.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    JwtService jwtService;
    AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Optional<User> userExists = userRepository.findByUserName(request.email());
        if (userExists.isPresent()) {
            throw new SecurityException("User already exists");//todo: custom exception
        }
        User newUser = User.builder()
                .role(Role.User)
                .fullName(request.firstName() + " " + request.lastName())
                .userName(request.email())
                .password(passwordEncoder.encode(request.password()))
                .creationDate(LocalDate.now())
                .build();
        userRepository.save(newUser);
        String token = jwtService.generateTokenNoClaims(newUser);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticateRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        // if code reaches here it means that the user completely authenticated!
        // otherwise an exception is thrown!
        Optional<User> user =
                Optional.ofNullable(userRepository.findByUserName(request.email()).orElseThrow(
                        () -> new UsernameNotFoundException("User not found")));

        String token = jwtService.generateTokenNoClaims(user.get());
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

}
