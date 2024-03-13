package com.example.repo;

import com.example.entities.ImageEntity;
import com.sun.istack.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    @Query("SELECT DISTINCT i FROM Image i LEFT JOIN FETCH i.hashtags")
    List<ImageEntity> findAll();
    List<ImageEntity> findByName(String fileName);
    Optional<ImageEntity> findById(@NotNull Long id);

    List<ImageEntity> findByDescriptionContaining(String keyword);

    @Query("SELECT i FROM Image i JOIN i.hashtags h WHERE h.name = :hashtagName")
    List<ImageEntity> findByHashtagName(@Param("hashtagName") String hashtagName);
}
