package com.rememberme.user.controller;

import com.rememberme.user.dto.UserResponseDto;
import com.rememberme.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    public ResponseEntity<UserResponseDto> getUserInfo(Authentication authentication) {
        String phone = authentication.getName();
        UserResponseDto userResponseDto = userService.getUserInfoByPhone(phone);
        return ResponseEntity.ok(userResponseDto);
    }
}