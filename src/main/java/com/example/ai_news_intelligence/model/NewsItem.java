package com.example.ai_news_intelligence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class NewsItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String uniqueId;

    private String queryParam;

    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String body;

    private String summary;
    private String tags;
    private String relevanceScore;
    private String sourceUrl;
    private String publisher;
    private String publishedAt;
    private String ingestedAt;

    private String videoUrl;
    private String imageUrl;
    private String mediaJustification;

    private String wikiSnippet;
    private String socialSentiment;
    private String searchTrend;

    private String geoLat;
    private String geoLng;
    private String geoMapUrl;
}
