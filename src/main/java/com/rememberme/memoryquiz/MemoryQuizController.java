package com.rememberme.memoryquiz;

import com.rememberme.gcs.GcsService.GcsService;
import com.rememberme.memoryquiz.dto.MemoryQuizRequestDto;
import com.rememberme.memoryquiz.dto.MemoryQuizResponseDto;
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
public class MemoryQuizController {

    private final MemoryQuizService memoryQuizService;
    private final GcsService GCSService;

    // 이것만 환자도 접근 가능!, 나머지는 보호자만 접근 가능
    @ApiOperation(value = "사용자의 모든 MemoryQuiz 조회")
    @GetMapping("/memory/all")
    public ResponseEntity<List<MemoryQuizResponseDto>> getMemoryAll(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        List<MemoryQuizResponseDto> result = memoryQuizService.getMemoryAllByUserId(userId);
        return ResponseEntity.ok(result);
    }
    
    @ApiOperation(value = "사용자의 개별 MemoryQuiz 상세 조회")
    @GetMapping("/memory/detail/{memoryId}")
    public ResponseEntity<MemoryQuizResponseDto> getMemoryOne(@PathVariable Long memoryQuizId) {
        MemoryQuizResponseDto memoryQuizResponseDto = memoryQuizService.getMemoryQuizByMemoryQuizId(memoryQuizId);
        return ResponseEntity.ok(memoryQuizResponseDto);
    }

    @ApiOperation(value = "MemoryQuiz 저장")
    @PostMapping("/memory")
    public ResponseEntity saveMemoryQuiz(
            Authentication authentication,
            @RequestBody MemoryQuizRequestDto memoryQuizRequestDto ) {
        Long userId = Long.parseLong(authentication.getName());
        memoryQuizService.saveMemory(userId, memoryQuizRequestDto);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @ApiOperation(value = "MemoryQuiz 수정")
    @PatchMapping("/memory/{memoryQuizId}")
    public ResponseEntity updateMemoryQuiz(
            @PathVariable Long memoryQuizId,
            @RequestBody MemoryQuizRequestDto memoryQuizRequestDto) {
        memoryQuizService.updateMemory(memoryQuizId, memoryQuizRequestDto);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @ApiOperation(value = "MemoryQuiz 삭제")
    @DeleteMapping("/memory/{memoryQuizId}")
    public ResponseEntity deleteMemory(@PathVariable Long memoryQuizId) {
        memoryQuizService.deleteMemory(memoryQuizId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "MemoryQuiz 이미지 업로드")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, value = "/image/{memoryQuizId}")
    public String addImageFile (
            @PathVariable Long memoryQuizId,
            @RequestParam MultipartFile file){
        String fileUrl = GCSService.uploadFiles(file);
        memoryQuizService.addImageFile(memoryQuizId, fileUrl);
        return fileUrl;
    }
    
    @ApiOperation(value = "MemoryQuiz 오디오 업로드")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, value = "/audio/{memoryQuizId}")
    public String addAudioFile(
            @PathVariable Long memoryQuizId,
            @RequestParam MultipartFile file){
        String fileUrl = GCSService.uploadFiles(file);
        memoryQuizService.addAudioFile(memoryQuizId, fileUrl);
        return fileUrl;
    }
}