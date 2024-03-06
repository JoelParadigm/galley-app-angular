package com.example.dto;

import com.example.entities.HashtagEntity;
import lombok.*;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class HashtagDto {

    private Long id;
    private String name;
    private Set<ImageDto> images;

    public static HashtagDto of(HashtagEntity entity) {
        if (entity == null) {
            return null;
        }

        return HashtagDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .images(mapImagesToDto(entity))
                .build();
    }

    private static Set<ImageDto> mapImagesToDto(HashtagEntity entity) {
        return entity.getImages().stream()
                .map(ImageEntity -> ImageDto.builder().build())
                .collect(Collectors.toSet());
    }
}