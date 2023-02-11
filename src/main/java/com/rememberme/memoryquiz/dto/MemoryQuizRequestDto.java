package com.rememberme.memoryquiz.dto;

import com.rememberme.memoryquiz.entity.MemoryQuiz;
import com.rememberme.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemoryQuizRequestDto {

    private String title;

    private String content;

    private String tagWho;

    private String tagWhen;

    private String tagWhere;

    private String tagWhat;

    public MemoryQuiz toEntity(User user) {
        return MemoryQuiz.builder()
                .user(user)
                .title(title)
                .content(content)
                .tagWho(tagWho)
                .tagWhen(tagWhen)
                .tagWhere(tagWhere)
                .tagWhat(tagWhat)
                .build();
    }
}