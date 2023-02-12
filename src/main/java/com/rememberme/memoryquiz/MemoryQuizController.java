package com.rememberme.memoryquiz;

import com.rememberme.gcs.GcsService.GcsService;
import com.rememberme.memoryquiz.dto.MemoryQuizRequestDto;
import com.rememberme.memoryquiz.dto.MemoryQuizResponseDto;
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

    @GetMapping("/memory/all")
    public ResponseEntity<List<MemoryQuizResponseDto>> getMemoryAll(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        List<MemoryQuizResponseDto> result = memoryQuizService.getMemoryAllByUserId(userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/memory/detail/{memoryId}")
    public ResponseEntity<MemoryQuizResponseDto> getMemoryOne(@PathVariable Long memoryId) {
        MemoryQuizResponseDto memoryQuizResponseDto = memoryQuizService.getMemoryByMemoryId(memoryId);
        return ResponseEntity.ok(memoryQuizResponseDto);
    }

    @PostMapping("/memory")
    public ResponseEntity saveMemory(
            Authentication authentication,
            @RequestBody MemoryQuizRequestDto memoryQuizRequestDto ) {
        Long userId = Long.parseLong(authentication.getName());
        memoryQuizService.saveMemory(userId, memoryQuizRequestDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/memory/{memoryId}")
    public ResponseEntity updateMemory(
            @PathVariable Long memoryId,
            @RequestBody MemoryQuizRequestDto memoryQuizRequestDto) {
        memoryQuizService.updateMemory(memoryId, memoryQuizRequestDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/memory/{memoryId}")
    public ResponseEntity deleteMemory(@PathVariable Long memoryId) {
        memoryQuizService.deleteMemory(memoryId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, value = "/image/{memoryId}")
    public String addImageFile (
            @PathVariable Long memoryId,
            @RequestParam MultipartFile file){
        String fileUrl = GCSService.uploadFiles(file);
        memoryQuizService.addImageFile(memoryId, fileUrl);
        return fileUrl;
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, value = "/audio/{memoryId}")
    public String addAudioFile(
            @PathVariable Long memoryId,
            @RequestParam MultipartFile file){
        String fileUrl = GCSService.uploadFiles(file);
        memoryQuizService.addAudioFile(memoryId, fileUrl);
        return fileUrl;
    }
}