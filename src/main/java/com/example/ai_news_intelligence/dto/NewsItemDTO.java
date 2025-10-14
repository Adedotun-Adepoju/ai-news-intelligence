package com.example.ai_news_intelligence.dto;

import com.example.ai_news_intelligence.dto.meta.Context;
import com.example.ai_news_intelligence.dto.meta.Media;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class NewsItemDTO {

    private String id;
    private String title;
    private String body;
    private String summary;
    private List<String> tags;
    @JsonProperty("relevance_score")
    private String relevanceScore;

    @JsonProperty("source_url")
    private String sourceUrl;

    @JsonProperty("publisher")
    private String publisher;

    @JsonProperty("published_at")
    private String publishedAt;

    @JsonProperty("ingested_at")
    private String ingestedAt;

    private Media media;
    private Context context;
}
