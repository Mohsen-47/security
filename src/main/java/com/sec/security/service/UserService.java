package com.sec.security.service;

import com.sec.security.model.User;
import com.sec.security.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;

    public String findUserName(String userName){
        Optional<User> obj = userRepository.findByUsername(userName);
        if(obj.isPresent()){
            return obj.get().getUsername();
        } else
            throw new SecurityException("user does not exist!");
    }
}
