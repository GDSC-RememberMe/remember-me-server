package com.rememberme.user.entity;

import com.rememberme.family.entity.Family;
import com.rememberme.user.entity.enumType.Role;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(length = 20)
    private String username; // 사용자 아이디

    private String password;

    private String nickname; // 사용자 설정 닉네임

    @Column(length = 15, unique = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "profile_img", nullable = true)
    private String profileImg;

    @Column(nullable = true)
    private LocalDate birth;

    @Column(nullable = true)
    private String address;

    @Column(name = "push_cnt")
    @ColumnDefault("0")
    private int pushCnt;

    @ColumnDefault("true")
    private boolean activated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_id")
    private Family family;

    public void saveProfileImg(String profileImg){
        this.profileImg = profileImg;
    }

    // 보호자가 환자 관계 설정
    public void saveFamily(Family family){
        this.family = family;
    }
}