package com.example.ai_news_intelligence.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Geo {
    private String lat;
    private String lng;
    @JsonProperty("map_url")
    private String mapUrl;
}