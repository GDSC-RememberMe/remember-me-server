package com.rememberme.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JoinRequestDto {

    @NotNull
    private String username;

    @NotNull
    private String password;

    private String nickname;

    @NotNull
    private String phone;

    @NotNull
    private String role;

    private String profileImg;

    private LocalDate birth;

    private String address;

    @ColumnDefault("0")
    private int pushCnt;

    @ColumnDefault("true")
    private boolean activated;
}