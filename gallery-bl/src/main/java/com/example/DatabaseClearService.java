package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabaseClearService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void clearDatabase() {
        jdbcTemplate.execute("DELETE FROM images_hashtags");
        jdbcTemplate.execute("DELETE FROM image");
        jdbcTemplate.execute("DELETE FROM hashtag");
    }
}
