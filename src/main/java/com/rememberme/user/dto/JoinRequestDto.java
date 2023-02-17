package com.rememberme.user.dto;

import com.rememberme.user.entity.enumType.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JoinRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String phone;

    private String nickname;

    @NotBlank
    private String role;

    @NotBlank
    @Pattern(regexp = "^(19[0-9][0-9]|20\\d{2})-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$", message = "생년월일이 yyyy-MM-dd의 형식이어야 합니다.")
    private String birth;

    @NotBlank
    private String gender;

    private String profileImg;

    private String address;

    @ColumnDefault("true")
    private boolean isPushAgree;

    @ColumnDefault("0")
    private int pushCnt;

    @ColumnDefault("true")
    private boolean activated;
}