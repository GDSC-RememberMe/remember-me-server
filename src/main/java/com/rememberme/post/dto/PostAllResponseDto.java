package com.rememberme.post.dto;

import com.rememberme.post.Post;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostAllResponseDto {

    private String title;

    private String contents;

    public PostAllResponseDto(Post post) {
        this.title = post.getTitle();
        this.contents = post.getContents();
    }
}
