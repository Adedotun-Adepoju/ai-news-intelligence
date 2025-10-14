package com.example.ai_news_intelligence.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Media {
    @JsonProperty("featured_image_url")
    private String featuredImageUrl;

    @JsonProperty("relate_video_url")
    private String relatedVideoUrl;

    @JsonProperty("media_justification")
    private String mediaJustification;
}
