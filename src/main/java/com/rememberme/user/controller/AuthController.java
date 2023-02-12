package com.rememberme.user.controller;

import com.rememberme.user.dto.JoinRequestDto;
import com.rememberme.user.dto.LoginRequestDto;
import com.rememberme.user.dto.TokenDto;
import com.rememberme.user.dto.UserResponseDto;
import com.rememberme.user.service.UserService;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "회원 가입")
    @PostMapping("/join")
    public ResponseEntity<UserResponseDto> join(@RequestBody JoinRequestDto joinRequestDto) {
        return ResponseEntity.ok(userService.join(joinRequestDto));
    }
    
    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(userService.login(loginRequestDto));
    }

    @ApiOperation(value = "토큰 재발급", notes = "AccessToken 만료시, RefreshToken 검증 후 AccessToken와 RefreshToken 재발급")
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissueToken(@RequestBody TokenDto tokenDto) {
        return ResponseEntity.ok(userService.reissue(tokenDto));
    }
}