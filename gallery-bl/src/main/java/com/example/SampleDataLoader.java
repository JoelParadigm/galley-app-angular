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
        Set<HashtagEntity> hashtags = new HashSet<>();
        ImageEntity image1 = new ImageEntity(null, null, "Image 1", "Description 1", LocalDateTime.now(), hashtags);
        ImageEntity image2 = new ImageEntity(null, null, "Image 2", "Description 1", LocalDateTime.now(), hashtags);
        ImageEntity image3 = new ImageEntity(null, null, "Image 3", "Description 1", LocalDateTime.now(), hashtags);

        galleryService.saveImage(image1);
        //galleryService.saveImage(image1);
        galleryService.saveImage(image2);
        galleryService.saveImage(image3);

    }
}