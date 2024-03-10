package com.example;

import com.example.entities.HashtagEntity;
import com.example.entities.ImageEntity;
import com.example.repo.HashtagRepository;
import com.example.repo.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GalleryService {

    private final HashtagRepository hashtagRepository;
    private final ImageRepository imageRepository;

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
    public ImageEntity saveImage(String imageName, String description, LocalDateTime uploadDate, byte[] imageData, List<String> hashtagNames) {
        List<HashtagEntity> hashtags = hashtagRepository.findByNameIn(hashtagNames);

        // Create new hashtags if not found
        List<String> existingHashtags = hashtags.stream().map(HashtagEntity::getName).collect(Collectors.toList());
        for (String hashtagName : hashtagNames) {
            if (!existingHashtags.contains(hashtagName)) {
                HashtagEntity newHashtag = new HashtagEntity();
                newHashtag.setName(hashtagName);
                hashtags.add(hashtagRepository.save(newHashtag));
            }
        }

        ImageEntity image = new ImageEntity();
        image.setName(imageName);
        image.setDescription(description);
        image.setUploadDate(uploadDate);
        image.setImageData(imageData);
        image.setHashtags(new HashSet<>(hashtags)); // Convert to Set

        return imageRepository.save(image);
    }
}
