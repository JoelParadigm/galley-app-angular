package com.example.dto;

import com.example.entities.ImageEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ImageThumbnailDto {

    private Long id;
    private String name;
    private byte[] imageData;
    private Set<HashtagNameDto> hashtags;

    public static ImageThumbnailDto of(ImageEntity entity) {
        if (entity == null) {
            return null;
        }

        return ImageThumbnailDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .imageData(entity.getImagethumbnail())
                .hashtags(new HashSet<>())
                .build();
    }

}