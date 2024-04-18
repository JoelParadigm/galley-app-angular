package com.example;

import com.example.dto.HashtagNameDto;
import com.example.dto.ImageDto;
import com.example.entities.HashtagEntity;
import com.example.entities.ImageEntity;
import com.example.repo.HashtagRepository;
import com.example.repo.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GalleryService {

    private final HashtagRepository hashtagRepository;
    private final ImageRepository imageRepository;
    private final ImageService imageService;

    public List<HashtagEntity> getAllHashtags() {
        return hashtagRepository.findAll();
    }

    public List<ImageDto> getAllImages() {
        List<ImageEntity> images = imageRepository.findAll();
        return images.stream()
                .map(ImageDto::of)
                .collect(Collectors.toList());
    }

    public ImageDto saveImage(ImageEntity image) {
        return ImageDto.of(imageRepository.save(image));
    }


    @Transactional
    public ImageDto getImageById(Long id) {
        System.out.println();
        if (id == null) {
            return null;
        }

        ImageEntity imageEntity = imageRepository.findById(id).orElse(null);
        if (imageEntity != null) {

            return ImageDto.of(imageEntity);
        }
        return null;
    }

    @Transactional
    public Long saveOrUpdateImage(ImageDto imageDto) {
        ImageEntity image = imageDto.getId() != null ?
                imageRepository.findById(imageDto.getId()).get()
                : new ImageEntity();
        image.setName(imageDto.getName());
        image.setDescription(imageDto.getDescription());
        image.setUploadDate(imageDto.getUploadDate());
        image.setImageData(imageDto.getImageData());
        if(imageDto.getImageThumbnail() != null)
            image.setImagethumbnail(imageDto.getImageThumbnail());
        else
            image.setImagethumbnail(imageService.generateThumbnail(imageDto.getImageData(), 400, 400));
        image.setHashtags(getAndUpdateHashtags(imageDto.getHashtags()));

        return saveImage(image).getId();
    }

    private Set<HashtagEntity> getAndUpdateHashtags(Set<HashtagNameDto> hashtagNames){

        if(hashtagNames.isEmpty()) return new HashSet<>();
        List<String> hashtagNameStrings = hashtagNames.stream()
                .map(HashtagNameDto::getName)
                .collect(Collectors.toList());

        List<HashtagEntity> hashtags = hashtagRepository.findByNameIn(hashtagNameStrings);

        List<String> existingHashtags = hashtags.stream()
                .map(HashtagEntity::getName)
                .collect(Collectors.toList());
        for (HashtagNameDto hashtagName : hashtagNames) {
            if (!existingHashtags.contains(hashtagName.getName())) {
                HashtagEntity newHashtag = new HashtagEntity();
                newHashtag.setName(hashtagName.getName());
                hashtags.add(hashtagRepository.save(newHashtag));
            }
        }
        return new HashSet<>(hashtags);
    }

    @Transactional
    public void deleteImageById(Long imageId) {
        imageRepository.deleteById(imageId);
    }
}
