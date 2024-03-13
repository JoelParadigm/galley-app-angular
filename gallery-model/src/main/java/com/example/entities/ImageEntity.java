package com.example.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Table(name = "Image")
@Entity(name = "Image")
@AllArgsConstructor
@NoArgsConstructor
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "image", columnDefinition = "bytea")
    private byte[] imageData;

    @Column(name = "thumbnail", columnDefinition = "bytea")
    private byte[] imagethumbnail;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "submit_date")
    private LocalDateTime uploadDate;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "images_hashtags",
            joinColumns = @JoinColumn(name = "image_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
    private Set<HashtagEntity> hashtags = new HashSet<>();

    public void addHashtag(HashtagEntity hashtag) {
        this.hashtags.add(hashtag);
        hashtag.getImages().add(this);
    }
}