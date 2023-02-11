package com.rememberme.memoryquiz;

import com.rememberme.memoryquiz.dto.MemoryQuizRequestDto;
import com.rememberme.memoryquiz.dto.MemoryQuizResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemoryQuizController {

    private final MemoryQuizService memoryQuizService;

    @GetMapping("/memory/all/{userId}")
    public ResponseEntity<List<MemoryQuizResponseDto>> getMemoryAll(@PathVariable Long userId) {
        List<MemoryQuizResponseDto> result = memoryQuizService.getMemoryAllByUserId(userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/memory/detail/{memoryId}")
    public ResponseEntity<MemoryQuizResponseDto> getMemoryOne(@PathVariable Long memoryId) {
        MemoryQuizResponseDto memoryQuizResponseDto = memoryQuizService.getMemoryByMemoryId(memoryId);
        return ResponseEntity.ok(memoryQuizResponseDto);
    }

    @PostMapping("/memory/{userId}")
    public ResponseEntity saveMemory(
            @PathVariable Long userId,
            @RequestBody MemoryQuizRequestDto memoryQuizRequestDto ) {
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
}