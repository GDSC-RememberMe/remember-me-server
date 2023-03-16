package com.rememberme.memory.dto;

import com.rememberme.family.entity.Family;
import com.rememberme.memory.entity.Memory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemoryRequestDto {

    private String title;

    private String content;

    private String tagWho;

    private String tagWhen;

    private String tagWhere;

    private String tagWhat;

    public Memory saveMemoryQuizWithFamily(Family family) {

        return Memory.builder()
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