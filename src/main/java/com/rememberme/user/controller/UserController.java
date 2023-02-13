package com.rememberme.user.controller;

import com.rememberme.gcs.GcsService.GcsService;
import com.rememberme.user.dto.UserResponseDto;
import com.rememberme.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final GcsService GCSService;
    
    @ApiOperation(value = "사용자 프로필 이미지 저장")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, value = "/profile")
    public String addProfileImage(
            Authentication authentication,
            @RequestParam MultipartFile file){
        Long userId = Long.parseLong(authentication.getName());
        String fileUrl = GCSService.uploadFiles(file);
        userService.addProfileImage(userId, fileUrl);
        return fileUrl;
    }

    @ApiOperation(value = "사용자 정보 조회")
    @GetMapping("/info")
    public ResponseEntity<UserResponseDto> getUserInfo(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        UserResponseDto userResponseDto  = userService.getUserInfoByUserId(userId);
        return ResponseEntity.ok(userResponseDto);
    }
}