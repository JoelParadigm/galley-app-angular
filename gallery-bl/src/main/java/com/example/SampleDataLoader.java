package com.example;

import com.example.entities.HashtagEntity;
import com.example.entities.ImageEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class SampleDataLoader {

    private final GalleryService galleryService;

    @PostConstruct
    public void loadSampleData() {


    }
}