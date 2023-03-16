package com.rememberme.memory.dto;

import com.rememberme.memory.entity.Memory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemoryResponseDto {

    private String title;

    private String content;

    private String tagWho;

    private String tagWhen;

    private String tagWhere;

    private String tagWhat;

    private String imgUrl;

    private String audioUrl;

    public MemoryResponseDto(Memory memory) {
        this.title = memory.getTitle();
        this.content = memory.getContent();
        this.tagWho = memory.getTagWho();
        this.tagWhen = memory.getTagWhen();
        this.tagWhere = memory.getTagWhere();
        this.tagWhat = memory.getTagWhat();
        this.imgUrl = memory.getImgUrl();
        this.audioUrl = memory.getAudioUrl();
    }
}