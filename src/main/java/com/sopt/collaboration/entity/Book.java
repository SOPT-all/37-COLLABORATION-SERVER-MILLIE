package com.sopt.collaboration.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column(nullable = false, length = 20)
    private String title;

    @Column(nullable = false, length = 20)
    private String authorName;

    @Column(length = 20)
    private String novelType;

    @Column(length = 20)
    private String publishedDate;

    @Column(nullable = false)
    private Float rating;

    @Column(nullable = false)
    private Integer fullReadRate;

    @Column(nullable = false)
    private Integer completionTime;

    @Column(nullable = false)
    private Boolean isAudiobook;

    @Column(columnDefinition = "TEXT")
    private String introduce;

    @Column(name = "book_image_key")
    private String bookImageKey;

    @Column(length = 20)
    private String voiceActor;

    @OneToOne(mappedBy = "book")
    private Banner banner;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @Builder
    private Book(String title, String authorName, String novelType, String publishedDate, Float rating, Integer fullReadRate, Integer completionTime, Boolean isAudiobook, String introduce, String bookImageKey, String voiceActor) {
        this.title = title;
        this.authorName = authorName;
        this.novelType = novelType;
        this.publishedDate = publishedDate;
        this.rating = rating;
        this.fullReadRate = fullReadRate;
        this.completionTime = completionTime;
        this.isAudiobook = isAudiobook;
        this.introduce = introduce;
        this.bookImageKey = bookImageKey;
        this.voiceActor = voiceActor;
    }
}
