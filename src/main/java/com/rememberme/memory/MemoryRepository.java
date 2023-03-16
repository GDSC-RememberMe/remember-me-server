package com.rememberme.memory;

import com.rememberme.memory.entity.Memory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemoryRepository extends JpaRepository<Memory, Long> {

     Optional<Memory> findById(Long memoryId);

     List<Memory> findAllByFamilyId(Long familyId);
}