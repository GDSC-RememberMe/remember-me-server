package com.rememberme.memoryquiz;

import com.rememberme.family.entity.Family;
import com.rememberme.memoryquiz.dto.MemoryQuizRequestDto;
import com.rememberme.memoryquiz.dto.MemoryQuizResponseDto;
import com.rememberme.memoryquiz.entity.MemoryQuiz;
import com.rememberme.user.entity.User;
import com.rememberme.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemoryQuizService {

    private final MemoryQuizRepository memoryQuizRepository;
    private final UserRepository userRepository;

    public List<MemoryQuizResponseDto> getMemoryAllByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NullPointerException("해당하는 사용자가 없습니다."));
        Long familyId = user.getFamily().getId();

        List<MemoryQuiz> memoryQuizList = memoryQuizRepository.findAllByFamilyId(familyId);
        return memoryQuizList.stream()
                .map(MemoryQuizResponseDto::new)
                .collect(Collectors.toUnmodifiableList());
    }

    public MemoryQuizResponseDto getMemoryQuizByMemoryQuizId(Long memoryId) {
        MemoryQuiz memoryQuiz = memoryQuizRepository.findById(memoryId)
                    .orElseThrow(() -> new NullPointerException("해당 기억이 없습니다."));

        return new MemoryQuizResponseDto(memoryQuiz);
    }

    public void saveMemory(Long userId, MemoryQuizRequestDto memoryQuizRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NullPointerException("해당하는 사용자가 없습니다."));

        Family family = user.getFamily();
        MemoryQuiz memoryQuiz = memoryQuizRequestDto.saveMemoryQuizWithFamily(family);
        memoryQuizRepository.save(memoryQuiz);
    }

    public void updateMemory(Long memoryQuizId, MemoryQuizRequestDto memoryQuizRequestDto) {
        MemoryQuiz memoryQuiz = memoryQuizRepository.findById(memoryQuizId)
                .orElseThrow(() -> new NullPointerException("해당 MemoryQuiz가 존재하지 않습니다."));

        memoryQuiz.updateMemoryQuiz(memoryQuizRequestDto);
        memoryQuizRepository.save(memoryQuiz);
    }

    public void deleteMemory(Long memoryQuizId) {
        memoryQuizRepository.deleteById(memoryQuizId);
    }

    public void addImageFile(Long memoryQuizId, String imageUrl) {
        MemoryQuiz memoryQuiz = memoryQuizRepository.findById(memoryQuizId)
                .orElseThrow(() -> new NullPointerException("해당 MemoryQuiz가 존재하지 않습니다."));

        memoryQuiz.addImageFile(imageUrl);
    }

    public void addAudioFile(Long memoryQuizId, String audioUrl) {
        MemoryQuiz memoryQuiz = memoryQuizRepository.findById(memoryQuizId)
                .orElseThrow(() -> new NullPointerException("해당 MemoryQuiz가 존재하지 않습니다."));
        memoryQuiz.addAudioFile(audioUrl);
    }
}