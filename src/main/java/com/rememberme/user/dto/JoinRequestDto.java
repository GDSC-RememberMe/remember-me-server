package com.rememberme.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JoinRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String nickname;

    @NotBlank
    private String phone;

    @NotBlank
    private String role;

    private String profileImg;

    @NotBlank
    private LocalDate birth;

    private String address;

    @ColumnDefault("0")
    private int pushCnt;

    @ColumnDefault("true")
    private boolean activated;
}