package com.rememberme.hashtag;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "hashtag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Hashtag {

    @Id
    @GeneratedValue
    @Column(name = "hashtag_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "hashtag")
    private List<HashtagPostLink> hashtagPostLinks = new ArrayList<>();
}