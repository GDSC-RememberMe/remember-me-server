package com.rememberme.memory.dto;

import com.rememberme.memory.entity.Memory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemoryResponseDto {

    private Long memoryId;

    private String title;

    private String content;

    private String imgUrl;

    private String audioUrl;

    public MemoryResponseDto(Memory memory) {
        this.memoryId = memory.getId();
        this.title = memory.getTitle();
        this.content = memory.getContent();
        this.imgUrl = memory.getImgUrl();
        this.audioUrl = memory.getAudioUrl();
    }
}