package com.rememberme.user.dto;

import com.rememberme.user.entity.Member;
import com.rememberme.user.entity.enumType.Gender;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private String username;

    private String nickname;

    private String phone;

    private String role;

    private String profileImg;

    private LocalDate birth;

    private Gender gender;

    private String address;

    private boolean isPushAgree;

    private int pushCnt;

    public UserResponseDto(Member member) {
        this.phone = member.getPhone();
        this.username = member.getUsername();
        this.nickname = member.getNickname();
        this.role = member.getRole().toString();
        this.profileImg = member.getProfileImg();
        this.birth = member.getBirth();
        this.gender = member.getGender();
        this.address = member.getAddress();
        this.isPushAgree = member.isPushAgree();
        this.pushCnt = member.getPushCnt();
    }
}
