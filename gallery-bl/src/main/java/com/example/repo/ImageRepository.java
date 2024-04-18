package com.example.repo;

import com.example.entities.HashtagEntity;
import com.example.entities.ImageEntity;
import com.sun.istack.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.DoubleStream;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    Optional<ImageEntity> findById(@NotNull Long id);

    @Query(value = "SELECT i.id, h.id, h.name " +
            "FROM Image i " +
            "JOIN i.hashtags h " +
            "WHERE i.id IN :imageIds")
    List<Object[]> findAllImageIdHashtagByImageIds(@Param("imageIds") List<Long> imageIds);

//    List<Object[]> findObjects(Specification<Object[]> spec);

    Page<ImageEntity> findAll(Specification<ImageEntity> spec, Pageable pageable);//projection
}
