package com.rememberme.user.controller;

import com.rememberme.user.dto.JoinRequestDto;
import com.rememberme.user.dto.LoginRequestDto;
import com.rememberme.user.dto.TokenDto;
import com.rememberme.user.dto.UserResponseDto;
import com.rememberme.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<UserResponseDto> join(@RequestBody JoinRequestDto joinRequestDto) {
        return ResponseEntity.ok(userService.join(joinRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(userService.login(loginRequestDto));
    }
}