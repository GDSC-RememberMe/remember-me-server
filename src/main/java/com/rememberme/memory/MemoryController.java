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
    @ApiOperation(value = "사용자의 모든 Memory 조회")
    @GetMapping("/memory/all")
    public ResponseEntity<List<MemoryResponseDto>> getMemoryAll(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        List<MemoryResponseDto> result = memoryService.getMemoryAllByUserId(userId);
        return ResponseEntity.ok(result);
    }
    
    @ApiOperation(value = "사용자의 개별 Memory 상세 조회")
    @GetMapping("/memory/detail/{memoryId}")
    public ResponseEntity<MemoryResponseDto> getMemoryOne(@PathVariable("memoryId") Long memoryId) {
        MemoryResponseDto memoryResponseDto = memoryService.getMemoryQuizByMemoryQuizId(memoryId);
        return ResponseEntity.ok(memoryResponseDto);
    }

    @ApiOperation(value = "Memory 저장")
    @PostMapping("/memory")
    public ResponseEntity<Long> saveMemory(
            Authentication authentication,
            @RequestBody MemoryRequestDto memoryRequestDto) {
        Long userId = Long.parseLong(authentication.getName());
        Long memoryId = memoryService.saveMemory(userId, memoryRequestDto);
        return ResponseEntity.ok(memoryId);
    }
    
    @ApiOperation(value = "Memory 수정")
    @PatchMapping("/memory/{memoryId}")
    public ResponseEntity updateMemory(
            @PathVariable("memoryId") Long memoryId,
            @RequestBody MemoryRequestDto memoryRequestDto) {
        memoryService.updateMemory(memoryId, memoryRequestDto);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @ApiOperation(value = "Memory 삭제")
    @DeleteMapping("/memory/{memoryId}")
    public ResponseEntity deleteMemory(@PathVariable("memoryId") Long memoryId) {
        memoryService.deleteMemory(memoryId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "Memory 이미지 업로드")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, value = "/image/{memoryId}")
    public String addImageFile (
            @PathVariable("memoryId") Long memoryId,
            @RequestParam MultipartFile file){
        String fileUrl = GCSService.uploadFiles(file);
        memoryService.addImageFile(memoryId, fileUrl);
        return fileUrl;
    }
    
    @ApiOperation(value = "Memory 오디오 업로드")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, value = "/audio/{memoryId}")
    public String addAudioFile(
            @PathVariable("memoryId") Long memoryId,
            @RequestParam MultipartFile file){
        String fileUrl = GCSService.uploadFiles(file);
        memoryService.addAudioFile(memoryId, fileUrl);
        return fileUrl;
    }

    @ApiOperation(value = "fcm 테스트용 - 랜덤 MemoryId 조회")
    @GetMapping("/memory/random")
    public ResponseEntity<MemoryRandomResponseDto> testFcm(Authentication authentication){
        Long memberId = Long.parseLong(authentication.getName());
        Long familyId = familyService.getFamilyIdByMemberId(memberId);
        MemoryRandomResponseDto memoryDto = memoryService.getRandomMemory(familyId);
        return ResponseEntity.ok(memoryDto);
    }
}