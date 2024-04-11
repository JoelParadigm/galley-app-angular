package com.example.search;

import com.example.dto.HashtagNameDto;
import com.example.dto.ImageThumbnailDto;
import com.example.dto.SearchCriteria;
import com.example.entities.ImageEntity;
import com.example.repo.ImageRepository;
import com.example.search.specifications.ImageSpecification;
import com.example.search.utils.ImageUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor
public class SearchService {

    private final ImageRepository imageRepository;

    public Page<ImageThumbnailDto> searchImages(SearchCriteria criteria, Pageable pageable) {
        Specification<ImageEntity> spec = ImageSpecification.buildSpecification(criteria);
        Page<ImageThumbnailDto> page = getCurrentPage_iId_iName_iThumbnail(spec, pageable);
        Map<Long, Set<HashtagNameDto>> map = getMapOf_iId_to_TagsList(page);
        for (ImageThumbnailDto dto : page)
            dto.setHashtags(map.get(dto.getId()));

        return page;
    }

    private Page<ImageThumbnailDto> getCurrentPage_iId_iName_iThumbnail(
        Specification<ImageEntity> spec,
        Pageable pageable
    ){
        return imageRepository.findAll(spec, pageable)
            .map(ImageThumbnailDto::of);
    }

    private Map<Long, Set<HashtagNameDto>> getMapOf_iId_to_TagsList(Page<ImageThumbnailDto> page){
        List<Long> image_ids_in_page = ImageUtil.extractIdsFromPage(page);
        List<Object[]> iId_hId_hName = findAll_iId_hId_hName(image_ids_in_page);
        return ImageUtil.getHashtagSetMapByImageIds(iId_hId_hName);
    }

    private List<Object[]> findAll_iId_hId_hName(List<Long> imageIds) {
        return imageRepository.findAllImageIdHashtagByImageIds(imageIds);
    }
}
