package com.rememberme.user.controller;

import com.rememberme.user.dto.*;
import com.rememberme.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

    @ApiOperation(value = "아이디 중복 검사")
    @PostMapping("/validation/username")
    public ResponseEntity validateUsername(@RequestBody @Valid UsernameRequestDto usernameRequestDto) {
        userService.validateUsername(usernameRequestDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "해당 핸드폰 번호로 이미 가입한 회원인지 확인")
    @PostMapping("/validation/phone")
    public ResponseEntity validatePhone(@RequestBody @Valid PhoneRequestDto phoneRequestDto) {
        userService.validatePhone(phoneRequestDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}