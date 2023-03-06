package com.rememberme.user.entity;

import com.rememberme.family.entity.Family;
import com.rememberme.user.entity.enumType.Gender;
import com.rememberme.user.entity.enumType.Role;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @NotNull
    @Column(unique = true)
    private String username; // 사용자 아이디

    @NotNull
    private String password;

    @NotNull
    private String nickname; // 사용자 설정 닉네임

    @Column(unique = true)
    @NotNull @Length(min=9, max= 11)
    private String phone;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "profile_img")
    private String profileImg;

    @NotNull
    private LocalDate birth;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String address;

    @ColumnDefault("true")
    @Column(name = "is_push_agree")
    private boolean isPushAgree;

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