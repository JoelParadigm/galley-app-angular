package com.example.dto;

import com.example.entities.ImageEntity;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ImageDto {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime uploadDate;
    private byte[] imageData;
    private Set<HashtagNameDto> hashtags;

    public static ImageDto of(ImageEntity entity) {
        if (entity == null) {
            return null;
        }

        return ImageDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .uploadDate(entity.getUploadDate())
                .imageData(entity.getImageData())
                .hashtags(mapTagsToDto(entity))
                .build();
    }

    private static Set<HashtagNameDto> mapTagsToDto(ImageEntity entity) {
        return entity.getHashtags().stream()
                .map(hashtagEntity -> HashtagNameDto.builder()
                        .id(hashtagEntity.getId())
                        .name(hashtagEntity.getName())
                        .build())
                .collect(Collectors.toSet());
    }
}