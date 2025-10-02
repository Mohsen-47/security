package com.sec.security.service.Impl;

import com.sec.security.model.User;
import com.sec.security.model.dto.UserDetail;
import com.sec.security.model.dto.requests.ChangePasswordRequest;
import com.sec.security.model.dto.response.UserDataResponse;
import com.sec.security.repository.UserRepository;
import com.sec.security.service.api.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;


    @Override
    public UserDataResponse getUserData(String userName) {
        Optional<User> obj = userRepository.findByUserName(userName);
        if (obj.isPresent()) {
            UserDetail user = UserDetail.buildFromUser(obj.get());
            return UserDataResponse.builder()
                            .fullName(user.getFullName())
                            .role(user.getRole().name())
                            .build();
        }
        //throw new SecurityException("user does not exist!");//todo: custom exception
        return null;
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest request) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails userDetails = (UserDetails) securityContext.getAuthentication().getPrincipal();

        Optional<User> obj = userRepository.findByUserName(userDetails.getUsername());
        if (obj.isPresent()) {
            if (passwordEncoder.matches(request.oldPassword(), userDetails.getPassword())) {
                User user = obj.get();
                user.setPassword(request.newPassword());
                userRepository.save(user);
            } else
                throw new SecurityException("wrong current password password!");//todo: custom exception
        }
    }


}
