package com.example.repo;

import com.example.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    List<ImageEntity> findByName(String fileName);

    List<ImageEntity> findByDescriptionContaining(String keyword);

    @Query("SELECT i FROM Image i JOIN i.hashtags h WHERE h.name = :hashtagName")
    List<ImageEntity> findByHashtagName(@Param("hashtagName") String hashtagName);
}
