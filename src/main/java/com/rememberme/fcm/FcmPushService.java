package com.rememberme.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.rememberme.fcm.dto.FcmPushDto;
import com.rememberme.memory.MemoryService;
import com.rememberme.memory.dto.MemoryRandomResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmPushService {

    @Value("${fcm.key.path}")
    private String FCM_PRIVATE_KEY;

    @Value("${fcm.key.scope}")
    private String FIREBASE_SCOPE;

    private FirebaseMessaging firebaseMessaging;

    private final MemoryService memoryService;

    // 1. fcm 기본 설정 진행
    @PostConstruct // 의존성 주입 후, 실행
    public void fcmInit() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(FCM_PRIVATE_KEY).getInputStream())
                .createScoped((Arrays.asList(FIREBASE_SCOPE)));

        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(googleCredentials)
                .build();

//        if (FirebaseApp.getApps().isEmpty()) {
//            FirebaseApp.initializeApp(firebaseOptions);
//            throw new RuntimeException("Firebase 어플리케이션이 등록되지 않았습니다.");
//        }
        FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions);
        this.firebaseMessaging = FirebaseMessaging.getInstance(app);
    }

    // 2. 메시지 전송  - 테스트용
    public void pushAlarmTest(Long familyId, String token) throws FirebaseMessagingException {
        log.info("알림 테스트 시작");

        MemoryRandomResponseDto memoryDto = memoryService.getRandomMemory(familyId);

        FcmPushDto fcmPushDto = FcmPushDto.builder()
                .memoryId(memoryDto.getMemoryId())
                .memoryTitle(memoryDto.getTitle() + "추억이 기억나시나요?")
                .build();

        Message message = createPushMessage(fcmPushDto, token);
        this.firebaseMessaging.send(message);
    }

    // Message 형식으로 변경
    private Message createPushMessage(FcmPushDto data, String token) {
        Notification notification = new Notification("Remember Me", data.getMemoryTitle());

        Message message = Message.builder()
                .setToken(token) // 사용자 토큰
                .setNotification(notification)
                .putData("memoryId", data.getMemoryId().toString())
                .build();
        return message;
    }
}