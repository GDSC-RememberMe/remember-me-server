package com.rememberme.jwt.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefreshToken {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "refresh_token_id")
    private Long id;

    private String userId;

    private String refreshToken;


    private RefreshToken(String userId, String refreshToken) {
        this.userId = userId;
        this.refreshToken = refreshToken;
    }

    public static RefreshToken createToken(String userId, String refreshToken){
        return new RefreshToken(userId, refreshToken);
    }

    public void updateToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}