package com.example.ai_news_intelligence.dto;

import com.example.ai_news_intelligence.dto.meta.Context;
import com.example.ai_news_intelligence.dto.meta.Media;
import lombok.Data;

import java.util.List;

@Data
public class NewsItemDTO {
    private String id;
    private String title;
    private String body;
    private String summary;
    private List<String> tags;
    private String relevanceScore;
    private String sourceUrl;
    private String publisher;
    private String publishedAt;
    private String ingestedAt;
    private Media media;
    private Context context;
}
