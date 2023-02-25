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

    private int view;

    private int like;

    @Column(name = "created_at")
    private LocalDate createdAt;
}