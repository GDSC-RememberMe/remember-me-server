package com.rememberme.dementiaquiz.dto;

import com.rememberme.dementiaquiz.NostalgiaItem;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NostalgiaItemResponseDto {

    private String imgUrl;

    private String rightAnswer;

    private String wrongAnswer;
}
