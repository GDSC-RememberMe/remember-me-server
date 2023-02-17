package com.rememberme.user.dto;

import com.rememberme.user.entity.User;
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

    public UserResponseDto(User user) {
        this.phone = user.getPhone();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.role = user.getRole().toString();
        this.profileImg = user.getProfileImg();
        this.birth = user.getBirth();
        this.gender = user.getGender();
        this.address = user.getAddress();
        this.isPushAgree = user.isPushAgree();
        this.pushCnt = user.getPushCnt();
    }
}
