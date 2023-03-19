package com.rememberme.fcm;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.rememberme.fcm.dto.FcmRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FcmController {

    private final FcmPushService fcmPushService;

    // 테스트용
    @GetMapping("/fcm-test")
    public ResponseEntity testPush(
            Authentication authentication,
            @RequestBody FcmRequestDto fcmRequestDto) throws FirebaseMessagingException {
        Long memberId = Long.parseLong(authentication.getName());
        String token = fcmRequestDto.getFcmToken(); // 토큰 받는 방식 이후에 변경
        fcmPushService.pushAlarmTest(memberId, token);
        return new ResponseEntity(HttpStatus.OK);
    }
}