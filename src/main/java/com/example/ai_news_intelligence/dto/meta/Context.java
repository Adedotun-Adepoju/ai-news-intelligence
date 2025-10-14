package com.example.ai_news_intelligence.dto.meta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Context {
    private String wikipediaSnippet;
    private String socialSentiment;
    private String searchTrend;
    private Geo geo;
}
