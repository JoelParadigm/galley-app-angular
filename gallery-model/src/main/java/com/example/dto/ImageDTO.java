package com.example.dto;

import com.example.HashtagEntity;
import com.example.ImageEntity;
import lombok.Data;

import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

@Data
public class ImageDTO {
    private Long id;

    private Byte[] imageData;

    private String name;

    private String description;

    private String date;

    private Set<HashtagEntity> hashtags = new HashSet<>();

    public static ImageDTO convertToDTO(ImageEntity imageEntity) {
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setId(imageEntity.getId());
        imageDTO.setImageData(imageEntity.getImageData());
        imageDTO.setName(imageEntity.getName());
        imageDTO.setDescription(imageEntity.getDescription());
        imageDTO.setHashtags(imageEntity.getHashtags());
        return imageDTO;
    }
}
