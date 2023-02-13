package com.rememberme.memoryquiz.dto;

import com.rememberme.family.entity.Family;
import com.rememberme.memoryquiz.entity.MemoryQuiz;
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

    public MemoryQuiz saveMemoryQuizWithFamily(Family family) {

        return MemoryQuiz.builder()
                .title(title)
                .content(content)
                .tagWho(tagWho)
                .tagWhen(tagWhen)
                .tagWhere(tagWhere)
                .tagWhat(tagWhat)
                .family(family)
                .build();
    }
}