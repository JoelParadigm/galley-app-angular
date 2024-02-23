package com.example;

import lombok.*;

import javax.persistence.*;
import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Table(name = "Images")
@Entity(name = "Images")
@AllArgsConstructor
@NoArgsConstructor
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Lob
    @Column(name = "data")
    private Byte[] imageData;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "submit_date")
    private LocalDateTime date;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "image_hashtag",
            joinColumns = @JoinColumn(name = "image_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
    private Set<HashtagEntity> hashtags = new HashSet<>();


    public void addHashtag(HashtagEntity hashtag) {
        this.hashtags.add(hashtag);
        hashtag.getImages().add(this);
    }
}