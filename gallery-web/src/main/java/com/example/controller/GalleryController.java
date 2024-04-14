package com.example.controller;

import com.example.GalleryService;
import com.example.TagService;
import com.example.dto.HashtagNameDto;
import com.example.dto.ImageDto;
import com.example.dto.ImageThumbnailDto;
import com.example.dto.SearchCriteria;
import com.example.entities.HashtagEntity;
import com.example.search.SearchService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/gallery")
@AllArgsConstructor
public class GalleryController {

    private final SearchService searchService;
    private final GalleryService galleryService;
    private final TagService tagService;

    @GetMapping("/hashtags")
    public ResponseEntity<List<HashtagNameDto>> getAllHashtagNames() {
        List<HashtagEntity> hashtags = galleryService.getAllHashtags();
        List<HashtagNameDto> hashtagNameDtos = hashtags.stream()
                .map(HashtagNameDto::of)
                .collect(Collectors.toList());
        return new ResponseEntity<>(hashtagNameDtos, HttpStatus.OK);
    }

    @GetMapping("/images")
        public ResponseEntity<Page<ImageThumbnailDto>> getAllImages(
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        SearchCriteria criteria = new SearchCriteria("","",null);
        Pageable pageable = PageRequest.of(page, size);
        Page<ImageThumbnailDto> imagePage = searchService.searchImages(criteria, pageable);
        return new ResponseEntity<>(imagePage, HttpStatus.OK);
    }

    @GetMapping("/images/search")
    public ResponseEntity<Page<ImageThumbnailDto>> searchImages(
            @RequestParam(required = false, defaultValue = "") String description,
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "") String tags,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {
        SearchCriteria criteria = formatSearchCriteria(description, tags, name);
        Pageable pageable = PageRequest.of(page, size);
        Page<ImageThumbnailDto> imagePage = searchService.searchImages(criteria, pageable);
        return new ResponseEntity<>(imagePage, HttpStatus.OK);
    }

    @GetMapping("/images/view")
    public ResponseEntity<ImageDto> getImage(@RequestParam(required = true) String imageId){
        try {
            long id = Long.parseLong(imageId);
            ImageDto image = galleryService.getImageById(id);
            return (image != null) ?
                new ResponseEntity<>(image, HttpStatus.OK):
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/images/save")
    public ResponseEntity<ImageDto> saveImage(@RequestParam(required = true) ImageDto image){
            galleryService.saveOrUpdateImage(image);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/images/delete")
    public ResponseEntity<ImageDto> deleteImage(@RequestParam(required = true) long imageId){
        galleryService.deleteImageById(imageId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    
    private SearchCriteria formatSearchCriteria(
            String description,
            String unparsed_tags,
            String searchTitle)
    {
        List<String> tags = tagService.getCorrectTagsFromUserInput(unparsed_tags);
        return new SearchCriteria(searchTitle, description, tags);
    }
}
