package com.example.search.utils;

import com.example.dto.HashtagNameDto;
import com.example.dto.ImageThumbnailDto;
import org.springframework.data.domain.Page;

import java.util.*;
import java.util.stream.Collectors;

public class ImageUtil {
    public static List<Long> extractIdsFromPage(Page<ImageThumbnailDto> page) {
        return page.getContent().stream()
                .map(ImageThumbnailDto::getId)
                .collect(Collectors.toList());
    }

    public static Map<Long, Set<HashtagNameDto>> getHashtagSetMapByImageIds(List<Object[]> iId_hId_hName) {

        Map<Long, Set<HashtagNameDto>> map = new HashMap<>();
        for (Object[] obj : iId_hId_hName) {
            Long imageId = (Long) obj[0];
            Long hashtagId = (Long) obj[1];
            String hashtagName = (String) obj[2];
            Set<HashtagNameDto> hashtagSet = map.computeIfAbsent(imageId, k -> new HashSet<>());
            hashtagSet.add(new HashtagNameDto(hashtagId, hashtagName));
        }
        return map;
    }
}
