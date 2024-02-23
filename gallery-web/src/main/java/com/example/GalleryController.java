package com.example;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gallery")
@AllArgsConstructor
public class GalleryController {

    private final GalleryService galleryService;

    @GetMapping("/hashtags")
    public ResponseEntity<List<HashtagEntity>> getAllHashtags() {
        List<HashtagEntity> hashtags = galleryService.getAllHashtags();
        return new ResponseEntity<>(hashtags, HttpStatus.OK);
    }

    @GetMapping("/images")
    public ResponseEntity<List<ImageEntity>> getAllImages() {
        List<ImageEntity> images = galleryService.getAllImages();
        return new ResponseEntity<>(images, HttpStatus.OK);
    }
}
