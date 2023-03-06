package com.rememberme.post;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;

    private String contents;

    @Column(name = "view_cnt")
    private int viewCnt;

    @Column(name = "like_cnt")
    private int likeCnt;

    @Column(name = "created_at")
    private LocalDate createdAt;
}