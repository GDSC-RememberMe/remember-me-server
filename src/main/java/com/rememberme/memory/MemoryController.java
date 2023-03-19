package com.rememberme.memory;

import com.rememberme.family.FamilyService;
import com.rememberme.gcs.GcsService.GcsService;
import com.rememberme.memory.dto.MemoryRandomResponseDto;
import com.rememberme.memory.dto.MemoryRequestDto;
import com.rememberme.memory.dto.MemoryResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemoryController {

    private final MemoryService memoryService;
    private final FamilyService familyService;
    private final GcsService GCSService;

    // 이것만 환자도 접근 가능!, 나머지는 보호자만 접근 가능
    @ApiOperation(value = "사용자의 모든 MemoryQuiz 조회")
    @GetMapping("/memory/all")
    public ResponseEntity<List<MemoryResponseDto>> getMemoryAll(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        List<MemoryResponseDto> result = memoryService.getMemoryAllByUserId(userId);
        return ResponseEntity.ok(result);
    }
    
    @ApiOperation(value = "사용자의 개별 MemoryQuiz 상세 조회")
    @GetMapping("/memory/detail/{memoryId}")
    public ResponseEntity<MemoryResponseDto> getMemoryOne(@PathVariable Long memoryQuizId) {
        MemoryResponseDto memoryResponseDto = memoryService.getMemoryQuizByMemoryQuizId(memoryQuizId);
        return ResponseEntity.ok(memoryResponseDto);
    }

    @ApiOperation(value = "MemoryQuiz 저장")
    @PostMapping("/memory")
    public ResponseEntity saveMemoryQuiz(
            Authentication authentication,
            @RequestBody MemoryRequestDto memoryRequestDto) {
        Long userId = Long.parseLong(authentication.getName());
        memoryService.saveMemory(userId, memoryRequestDto);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @ApiOperation(value = "MemoryQuiz 수정")
    @PatchMapping("/memory/{memoryQuizId}")
    public ResponseEntity updateMemoryQuiz(
            @PathVariable Long memoryQuizId,
            @RequestBody MemoryRequestDto memoryRequestDto) {
        memoryService.updateMemory(memoryQuizId, memoryRequestDto);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @ApiOperation(value = "MemoryQuiz 삭제")
    @DeleteMapping("/memory/{memoryQuizId}")
    public ResponseEntity deleteMemory(@PathVariable Long memoryQuizId) {
        memoryService.deleteMemory(memoryQuizId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "MemoryQuiz 이미지 업로드")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, value = "/image/{memoryQuizId}")
    public String addImageFile (
            @PathVariable Long memoryQuizId,
            @RequestParam MultipartFile file){
        String fileUrl = GCSService.uploadFiles(file);
        memoryService.addImageFile(memoryQuizId, fileUrl);
        return fileUrl;
    }
    
    @ApiOperation(value = "MemoryQuiz 오디오 업로드")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, value = "/audio/{memoryQuizId}")
    public String addAudioFile(
            @PathVariable Long memoryQuizId,
            @RequestParam MultipartFile file){
        String fileUrl = GCSService.uploadFiles(file);
        memoryService.addAudioFile(memoryQuizId, fileUrl);
        return fileUrl;
    }

    @ApiOperation(value = "fcm 테스트용 - 랜덤 MemoryId 조회")
    @GetMapping("/memory/random")
    public ResponseEntity<MemoryRandomResponseDto> testFcm(Authentication authentication){
        Long memberId = Long.parseLong(authentication.getName());
        Long familyId = familyService.getFamilyByMemberId(memberId);
        MemoryRandomResponseDto memoryDto = memoryService.getRandomMemory(familyId);
        return ResponseEntity.ok(memoryDto);
    }
}