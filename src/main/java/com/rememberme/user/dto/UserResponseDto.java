package com.rememberme.user.dto;

import com.rememberme.user.entity.User;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private String phone;

    private String username;

    private String role;

    private String profileImg;

    private LocalDate birth;

    private String address;

    private int pushCnt;

    public UserResponseDto(User user) {
        this.phone = user.getPhone();
        this.username = user.getUsername();
        this.role = user.getRole().toString();
        this.profileImg = user.getProfileImg();
        this.birth = user.getBirth();
        this.address = user.getAddress();
        this.pushCnt = user.getPushCnt();
    }
}
