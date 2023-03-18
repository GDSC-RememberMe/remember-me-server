package com.rememberme.dementiaquiz.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NostalgiaItemQuizResultResponseDto {

    private long nostalgiaItemId;

    private String imgUrl;

    private boolean result;
}
