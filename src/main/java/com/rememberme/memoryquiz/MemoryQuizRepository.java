package com.rememberme.memoryquiz;

import com.rememberme.memoryquiz.entity.MemoryQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemoryQuizRepository extends JpaRepository<MemoryQuiz, Long> {

     Optional<MemoryQuiz> findById(Long memoryId);

     List<MemoryQuiz> findAllByUserId(Long userId);
}