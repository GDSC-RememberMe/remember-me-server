package com.rememberme.memoryquiz.dto;

import com.rememberme.memoryquiz.entity.MemoryQuiz;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemoryQuizResponseDto {

    private String title;

    private String content;

    private String tagWho;

    private String tagWhen;

    private String tagWhere;

    private String tagWhat;

    private String imgUrl;

    private String audioUrl;

    public MemoryQuizResponseDto(MemoryQuiz memoryQuiz) {
        this.title = memoryQuiz.getTitle();
        this.content = memoryQuiz.getContent();
        this.tagWho = memoryQuiz.getTagWho();
        this.tagWhen = memoryQuiz.getTagWhen();
        this.tagWhere = memoryQuiz.getTagWhere();
        this.tagWhat = memoryQuiz.getTagWhat();
        this.imgUrl = memoryQuiz.getImgUrl();
        this.audioUrl = memoryQuiz.getAudioUrl();
    }
}