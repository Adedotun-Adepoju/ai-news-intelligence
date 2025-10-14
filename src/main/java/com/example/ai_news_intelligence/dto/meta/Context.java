package com.example.ai_news_intelligence.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Context {
    @JsonProperty("wikipedia_snipped")
    private String wikipediaSnippet;

    @JsonProperty("social_sentiment")
    private String socialSentiment;

    @JsonProperty("search_trend")
    private String searchTrend;
    private Geo geo;
}
