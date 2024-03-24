package com.example;

import com.example.dto.HashtagNameDto;
import com.example.dto.ImageDto;
import com.example.dto.ImageThumbnailDto;
import com.example.entities.HashtagEntity;
import com.example.entities.ImageEntity;
import com.example.repo.HashtagRepository;
import com.example.repo.ImageRepository;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GalleryService {

    private final HashtagRepository hashtagRepository;
    private final ImageRepository imageRepository;

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
        if (id == null) {
            return null;
        }

        ImageEntity imageEntity = imageRepository.findById(id).orElse(null);
        if (imageEntity != null) {
            // Initialize tags eagerly
            imageEntity.getHashtags().size();
            return ImageDto.of(imageEntity);
        }
        return null;
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

        return saveImage(image).getId();
    }

    private Set<HashtagEntity> getAndUpdateHashtags(Set<HashtagNameDto> hashtagNames){

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

//    public List<ImageThumbnailDto> getAllImageThumbnailDtos() {
//        List<Object[]> imageObjects = imageRepository.findAllImagesForGalleryView();
//        List<Object[]> imageTagsObjects = imageRepository.findAllImageIdHashtagByImageIds(
//                getImageIdListFromObjectList(imageObjects));
//        Map<Long, Set<HashtagNameDto>> tagMap = getHashtagSetMapByImageIds(imageTagsObjects);
//        List<ImageThumbnailDto> imageThumbnailDtos = imageObjects.stream()
//                .map(objectArray -> {
//                    Long id = (Long) objectArray[0];
//                    String name = (String) objectArray[1];
//                    byte[] thumbnailData = (byte[]) objectArray[2];
//                    Set<HashtagNameDto> hashtags = tagMap.get(id);
//                    ImageThumbnailDto tempDto = new ImageThumbnailDto(id, name, thumbnailData, hashtags);
//                    return tempDto;
//                })
//                .collect(Collectors.toList());        return imageThumbnailDtos;
//    }
//
//    private List<Long> getImageIdListFromObjectList(List<Object[]> imageObjects){
//        List<Long> imageIdList = new ArrayList<>();
//        for (Object[] imageObject : imageObjects) {
//            Long imageId = (Long) imageObject[0];
//            imageIdList.add(imageId);
//        }
//        return imageIdList;
//    }
//
//    public static Map<Long, Set<HashtagNameDto>> getHashtagSetMapByImageIds(List<Object[]> iId_hId_hName) {
//        Map<Long, Set<HashtagNameDto>> map = new HashMap<>();
//
//        for (Object[] obj : iId_hId_hName) {
//            Long imageId = (Long) obj[0];
//            Long hashtagId = (Long) obj[1];
//            String hashtagName = (String) obj[2];
//
//            // Retrieve the set of hashtags for the current image ID
//            Set<HashtagNameDto> hashtagSet = map.computeIfAbsent(imageId, k -> new HashSet<>());
//
//            // Add the current hashtag to the set
//            hashtagSet.add(new HashtagNameDto(hashtagId, hashtagName));
//        }
//
//        return map;
//    }

//    public Set<HashtagNameDto> getHashtagsByImageId(Long imageId) {
//        Set<HashtagEntity> hashtags = imageRepository.findHashtagsByImageId(imageId);
//
//        // Map HashtagEntity objects to HashtagNameDto objects
//        Set<HashtagNameDto> hashtagDtos = new HashSet<>();
//        for (HashtagEntity hashtag : hashtags) {
//            hashtagDtos.add(HashtagNameDto.of(hashtag));
//        }
//
//        return hashtagDtos;
//    }

    @Transactional
    public void deleteImageById(Long imageId) {
        imageRepository.deleteById(imageId);
    }
}
