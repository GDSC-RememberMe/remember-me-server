package com.rememberme.dementiaquiz;

import com.rememberme.dementiaquiz.dto.NostalgiaItemResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NostalgiaItemService {

    private final NostalgiaItemRepository nostalgiaItemRepository;
    private final int NostalgiaItemCnt = 5;

    public List<NostalgiaItemResponseDto> getNostalgiaItems() {
        List<NostalgiaItem> nostalgiaItems = nostalgiaItemRepository.findRandomLimit10();
        List<NostalgiaItemResponseDto> result = new ArrayList<>();
        for (int i = 0; i < NostalgiaItemCnt ;i++) {
            NostalgiaItem evenNostalgiaItem = nostalgiaItems.get(i * 2);
            NostalgiaItem oddNostalgiaItem = nostalgiaItems.get(i * 2 +1);
            NostalgiaItemResponseDto newItem = NostalgiaItemResponseDto.builder()
                        .imgUrl(evenNostalgiaItem.getImgUrl())
                        .rightAnswer(evenNostalgiaItem.getTitle())
                        .wrongAnswer(oddNostalgiaItem.getTitle())
                        .build();
            result.add(newItem);
        }
        return result;
    }
}
