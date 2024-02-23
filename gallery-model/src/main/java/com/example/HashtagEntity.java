package com.example;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Table(name = "Hashtags")
@Entity(name = "Hashtags")
@AllArgsConstructor
@NoArgsConstructor
public class HashtagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long id;
    @Column(name = "hashtag_name")
    private String name;

    @ManyToMany(mappedBy = "hashtags")
    @Column(name = "images")
    private Set<ImageEntity> images = new HashSet<>();
}
