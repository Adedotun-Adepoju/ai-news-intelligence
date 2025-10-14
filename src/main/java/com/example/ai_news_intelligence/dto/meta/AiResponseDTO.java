package com.example.ai_news_intelligence.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AiResponseDTO {
    private String summary;
    private List<String> tags;

    @JsonProperty("relevance_score")
    private String relevanceScore;

    private String images;
    private String videos;

    @JsonProperty("media_justification")
    private String mediaJustification;

    @JsonProperty("wikipedia_topic")
    private String wikipediaTopic;

    @JsonProperty("search_keyword")
    private String searchKeyword;

    @JsonProperty("longitude")
    private String longitude;

    @JsonProperty("latitude")
    private String latitude;

    @JsonProperty("map_url")
    private String mapUrl;

    @JsonProperty("social_sentiment")
    private String socialSentiment;
}
