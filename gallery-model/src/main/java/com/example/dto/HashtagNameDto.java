package com.example.dto;

import com.example.HashtagEntity;
import lombok.Data;

@Data
public class HashtagNameDto {
    private Long id;
    private String name;
    public static HashtagDTO convertToDTO(HashtagEntity hashtagEntity) {
        HashtagDTO hashtagDTO = new HashtagDTO();
        hashtagDTO.setId(hashtagEntity.getId());
        hashtagDTO.setName(hashtagEntity.getName());
        return hashtagDTO;
    }
}
