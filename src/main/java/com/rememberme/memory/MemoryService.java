package com.rememberme.memory;

import com.rememberme.family.entity.Family;
import com.rememberme.memory.dto.MemoryRandomResponseDto;
import com.rememberme.memory.dto.MemoryRequestDto;
import com.rememberme.memory.dto.MemoryResponseDto;
import com.rememberme.memory.entity.Memory;
import com.rememberme.member.entity.Member;
import com.rememberme.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemoryService {

    private final MemoryRepository memoryRepository;
    private final MemberRepository memberRepository;

    public List<MemoryResponseDto> getMemoryAllByUserId(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new NullPointerException("해당하는 사용자가 없습니다."));
        Long familyId = member.getFamily().getId();

        List<Memory> memoryList = memoryRepository.findAllByFamilyId(familyId);
        return memoryList.stream()
                .map(MemoryResponseDto::new)
                .collect(Collectors.toUnmodifiableList());
    }

    public MemoryResponseDto getMemoryQuizByMemoryQuizId(Long memoryId) {
        Memory memory = memoryRepository.findById(memoryId)
                    .orElseThrow(() -> new NullPointerException("해당 기억이 없습니다."));

        return new MemoryResponseDto(memory);
    }

    public Long saveMemory(Long userId, MemoryRequestDto memoryRequestDto) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new NullPointerException("해당하는 사용자가 없습니다."));

        Family family = member.getFamily();
        Memory memory = memoryRequestDto.saveMemoryQuizWithFamily(family);
        Memory savedMemory = memoryRepository.save(memory);
        return savedMemory.getId();
    }

    public void updateMemory(Long memoryQuizId, MemoryRequestDto memoryRequestDto) {
        Memory memory = memoryRepository.findById(memoryQuizId)
                .orElseThrow(() -> new NullPointerException("해당 MemoryQuiz가 존재하지 않습니다."));

        memory.updateMemoryQuiz(memoryRequestDto);
        memoryRepository.save(memory);
    }

    public void deleteMemory(Long memoryQuizId) {
        memoryRepository.deleteById(memoryQuizId);
    }

    public void addImageFile(Long memoryQuizId, String imageUrl) {
        Memory memory = memoryRepository.findById(memoryQuizId)
                .orElseThrow(() -> new NullPointerException("해당 MemoryQuiz가 존재하지 않습니다."));

        memory.addImageFile(imageUrl);
    }

    public void addAudioFile(Long memoryQuizId, String audioUrl) {
        Memory memory = memoryRepository.findById(memoryQuizId)
                .orElseThrow(() -> new NullPointerException("해당 MemoryQuiz가 존재하지 않습니다."));
        memory.addAudioFile(audioUrl);
    }

    public MemoryRandomResponseDto getRandomMemory(Long familyId) {
        Optional<Memory> randomMemoryOpt = memoryRepository.findOneRandomMemoryIdByFamilyId(familyId);
        if (randomMemoryOpt.isEmpty()) {
            log.info("해당 사용자는 저장된 추억이 없습니다.");
            return null;
        }
        Memory randomMemory = randomMemoryOpt.get();
        String title = randomMemory.getTitle();
        Long memoryId = randomMemory.getId();
        return MemoryRandomResponseDto.builder().title(title).memoryId(memoryId).build();
    }

    public Memory getRandomMemoryAll(Long familyId) {
        Optional<Memory> randomMemoryOpt = memoryRepository.findOneRandomMemoryIdByFamilyId(familyId);
        if (randomMemoryOpt.isEmpty()) {
            log.info("해당 사용자는 저장된 추억이 없습니다.");
            return null;
        }
        Memory randomMemory = randomMemoryOpt.get();
        return randomMemoryOpt.get();
    }
}