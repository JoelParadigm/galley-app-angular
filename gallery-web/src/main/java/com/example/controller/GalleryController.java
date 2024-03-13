package com.example.controller;

import com.example.GalleryService;
import com.example.dto.HashtagNameDto;
import com.example.dto.ImageDto;
import com.example.entities.HashtagEntity;
import com.example.entities.ImageEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/gallery")
@AllArgsConstructor
public class GalleryController {

    private final GalleryService galleryService;

    @GetMapping("/images")
    public ResponseEntity<List<ImageDto>> getAllImages() {
        List<ImageEntity> images = galleryService.getAllImages();
        List<ImageDto> imageDtos = images.stream()
                .map(ImageDto::of)
                .collect(Collectors.toList());
        return new ResponseEntity<>(imageDtos, HttpStatus.OK);
    }

    @GetMapping("/hashtags")
    public ResponseEntity<List<HashtagNameDto>> getAllHashtagNames() {
        List<HashtagEntity> hashtags = galleryService.getAllHashtags();
        List<HashtagNameDto> hashtagNameDtos = hashtags.stream()
                .map(HashtagNameDto::of)
                .collect(Collectors.toList());
        return new ResponseEntity<>(hashtagNameDtos, HttpStatus.OK);
    }
}
