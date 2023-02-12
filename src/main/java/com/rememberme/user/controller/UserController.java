package com.rememberme.user.controller;

import com.rememberme.gcs.GcsService.GcsService;
import com.rememberme.user.dto.UserResponseDto;
import com.rememberme.user.service.CustomDetailsService;
import com.rememberme.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final GcsService GCSService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, value = "/profile/{userId}")
    public String addProfileImage(
            @PathVariable Long userId,
            @RequestParam MultipartFile file){
        String fileUrl = GCSService.uploadFiles(file);
        userService.addProfileImage(userId, fileUrl);
        return fileUrl;
    }

    @GetMapping("/info")
    public ResponseEntity<UserResponseDto> getUserInfo(Authentication authentication) {
        String userId = authentication.getName();
        UserResponseDto userResponseDto  = userService.getUserInfoByUserId(userId);
        return ResponseEntity.ok(userResponseDto);
    }
}