package com.rememberme.family.dto;

import com.rememberme.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FamilyResponseDto {

    private Long familyId;

    private Long userId; // 환자 id

    private String username;

    private String nickname;

    private String profileImg;

    public FamilyResponseDto of(Member member) {
        this.familyId = member.getFamily().getId();
        this.userId = member.getId();
        this.username = member.getUsername();
        this.nickname = member.getNickname();
        this.profileImg = member.getProfileImg();
        return this;
    }
}