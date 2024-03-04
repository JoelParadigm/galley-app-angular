package com.example.dto;

import com.example.entities.HashtagEntity;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class HashtagNameDto {
    private Long id;
    private String name;

    public static HashtagNameDto of(HashtagEntity entity) {
        if (entity == null) {
            return null;
        }
        return HashtagNameDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
