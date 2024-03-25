package com.example.search.specifications;

import com.example.dto.SearchCriteria;
import com.example.entities.HashtagEntity;
import com.example.entities.ImageEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ImageSpecification {
    public static Specification<ImageEntity> selectImageThumbnails() {
        return (root, query, builder) -> {
            query.multiselect(
                    root.get("id"),
                    root.get("name"),
                    root.get("imagethumbnail"));

            return query.getRestriction();
        };
    }

    public static Specification<ImageEntity> buildSpecification(SearchCriteria criteria) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getImageTitle() != null && !criteria.getImageTitle().isEmpty()) {
                predicates.add(builder.like(root.get("name"), "%" + criteria.getImageTitle() + "%"));
            }

            if (criteria.getDescription() != null && !criteria.getDescription().isEmpty()) {
                predicates.add(builder.like(root.get("description"), "%" + criteria.getDescription() + "%"));
            }

            if (criteria.getTagNames() != null && !criteria.getTagNames().isEmpty()) {
                Join<ImageEntity, HashtagEntity> hashtags = root.join("hashtags", JoinType.INNER);
                predicates.add(hashtags.get("name").in(criteria.getTagNames()));
                query.distinct(true);
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Object[]> findAll_iId_hId_hName(List<Long> imageIds) {
        return (root, query, builder) -> {
            Join<Object, Object> hashtags = root.join("hashtags", JoinType.INNER);
            query.multiselect(root.get("id"), hashtags.get("id"), hashtags.get("name"));
            return root.get("id").in(imageIds);
        };
    }


}
