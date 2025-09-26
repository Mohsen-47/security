package com.sec.security.controller;

import com.sec.security.model.dto.requests.ChangePasswordRequest;
import com.sec.security.model.dto.response.UserDataResponse;
import com.sec.security.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserDataController {
    private final UserService userService;

    @GetMapping("/{userName}")
    public ResponseEntity<UserDataResponse> getUserData(@PathVariable String userName) {
        return ResponseEntity.ok().body(userService.getUserData(userName));
    }
    @PostMapping("/change-password")
    public ResponseEntity<String> getUserData(@RequestBody ChangePasswordRequest request) {
        userService.changePassword(request);
        return ResponseEntity.ok().body("password has been changed");
    }
}
