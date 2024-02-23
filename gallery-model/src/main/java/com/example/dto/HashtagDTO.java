package com.example.dto;

import com.example.HashtagEntity;
import com.example.ImageEntity;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class HashtagDTO {
    private Long id;

    private String name;
    private Set<ImageEntity> images = new HashSet<>();

    public static HashtagDTO convertToDTO(HashtagEntity hashtagEntity) {
        HashtagDTO hashtagDTO = new HashtagDTO();
        hashtagDTO.setId(hashtagEntity.getId());
        hashtagDTO.setName(hashtagEntity.getName());
        hashtagDTO.setImages(hashtagEntity.getImages());
        return hashtagDTO;
    }
}
