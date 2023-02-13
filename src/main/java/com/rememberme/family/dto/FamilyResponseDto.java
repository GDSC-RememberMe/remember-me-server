package com.rememberme.family.dto;

import com.rememberme.user.entity.User;
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

    public FamilyResponseDto of(User user) {
        this.familyId = user.getFamily().getId();
        this.userId = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.profileImg = user.getProfileImg();
        return this;
    }
}