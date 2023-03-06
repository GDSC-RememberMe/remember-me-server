package com.rememberme.family;

import com.rememberme.family.dto.FamilyResponseDto;
import com.rememberme.family.entity.Family;
import com.rememberme.user.entity.Member;
import com.rememberme.user.entity.enumType.Role;
import com.rememberme.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FamilyService {

    private final FamilyRepository familyRepository;
    private final UserRepository userRepository;

    public List<FamilyResponseDto> searchFamilyByKeyword(String keyword) {
        List<Member> memberList = userRepository.findByUsernameContaining(keyword);

        return memberList.stream()
                .filter(user -> user.getRole().equals(Role.PATIENT))
                .map(user -> new FamilyResponseDto().of(user))
                .collect(Collectors.toUnmodifiableList());
    }

    public void setFamilyOfCaregiverUser(Long userId, Long familyId) {
        Member member = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 보호자 회원이 존재하지 않습니다."));
        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new RuntimeException("해당 환자 회원이 존재하지 않습니다."));

        member.saveFamily(family);
    }
}