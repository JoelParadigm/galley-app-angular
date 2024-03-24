package com.example.repo;

import com.example.entities.HashtagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HashtagRepository extends JpaRepository<HashtagEntity, Long> {
    List<HashtagEntity> findByNameIn(List<String> names);
}