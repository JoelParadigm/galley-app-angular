package com.example.repo;

import com.example.entities.HashtagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HashtagRepository extends JpaRepository<HashtagEntity, Long> {
    List<HashtagEntity> findByName(String tagName);

    List<HashtagEntity> findByNameIn(List<String> names);

    @Query("SELECT h FROM Hashtag h LEFT JOIN h.images i WHERE i.id IN :imageIds GROUP BY h HAVING COUNT(i) = 0")
    List<HashtagEntity> findFloatingTagsByImageIds(@Param("imageIds") List<Long> imageIds);
}
