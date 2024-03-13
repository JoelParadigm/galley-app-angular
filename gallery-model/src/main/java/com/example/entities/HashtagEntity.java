package com.example.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Table(name = "Hashtag")
@Entity(name = "Hashtag")
@AllArgsConstructor
@NoArgsConstructor
public class HashtagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "hashtags")
    @Column(name = "images")
    private Set<ImageEntity> images = new HashSet<>();
}
