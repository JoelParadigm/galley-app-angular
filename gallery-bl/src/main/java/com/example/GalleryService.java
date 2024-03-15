package com.example;

import com.example.dto.HashtagNameDto;
import com.example.dto.ImageDto;
import com.example.dto.ImageThumbnailDto;
import com.example.entities.HashtagEntity;
import com.example.entities.ImageEntity;
import com.example.repo.HashtagRepository;
import com.example.repo.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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

    public List<ImageEntity> getAllImages() {
        return imageRepository.findAll();
    }

    public ImageEntity saveImage(ImageEntity image) {
        return imageRepository.save(image);
    }

    public List<ImageEntity> getImagesByHashtag(String hashtagName) {
        return imageRepository.findByHashtagName(hashtagName);
    }

    @Transactional
    public Long saveOrUpdateImage(ImageDto imageDto) {



        ImageEntity image = imageDto.getId() != null ? imageRepository.findById(imageDto.getId()).get(): new ImageEntity();
        image.setName(imageDto.getName());
        image.setDescription(imageDto.getDescription());
        image.setUploadDate(imageDto.getUploadDate());
        image.setImageData(imageDto.getImageData());
        image.setImagethumbnail(imageDto.getImageThumbnail());
        image.setHashtags(getAndUpdateHashtags(imageDto.getHashtags()));

        return imageRepository.save(image).getId();
    }

    public Set<HashtagEntity> getAndUpdateHashtags(Set<HashtagNameDto> hashtagNames){

        List<String> hashtagNameStrings = hashtagNames.stream()
                .map(HashtagNameDto::getName)
                .collect(Collectors.toList());

        List<HashtagEntity> hashtags = hashtagRepository.findByNameIn(hashtagNameStrings);

        // Create new hashtags if not found
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

    public List<ImageDto> getAllImageDtos() {
        List<ImageEntity> images = getAllImages();
        List<ImageDto> imageDtos = images.stream()
                .map(ImageDto::of)
                .collect(Collectors.toList());
        return imageDtos;
    }

    public List<ImageThumbnailDto> getAllImageThumbnailDtos(int thumbnailWidth, int thumbnailHeight) {
        List<Object[]> imageObjects = imageRepository.findAllImagesForGalleryView();
        List<ImageThumbnailDto> imageThumbnailDtos = imageObjects.stream()
                .map(objectArray -> {
                    Long id = (Long) objectArray[0];
                    String name = (String) objectArray[1];
                    byte[] thumbnailData = (byte[]) objectArray[2];
                    Set<HashtagNameDto> hashtags = getHashtagsByImageId(id);
                    ImageThumbnailDto tempDto = new ImageThumbnailDto(id, name, thumbnailData, hashtags);
                    return tempDto;
                })
                .collect(Collectors.toList());        return imageThumbnailDtos;
    }

    public Set<HashtagNameDto> getHashtagsByImageId(Long imageId) {
        Set<HashtagEntity> hashtags = imageRepository.findHashtagsByImageId(imageId);

        // Map HashtagEntity objects to HashtagNameDto objects
        Set<HashtagNameDto> hashtagDtos = new HashSet<>();
        for (HashtagEntity hashtag : hashtags) {
            hashtagDtos.add(HashtagNameDto.of(hashtag));
        }

        return hashtagDtos;
    }
}
