package com.example.ai_news_intelligence.dto.response;

import com.example.ai_news_intelligence.dto.Message;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class QwenResponse {
    private List<Choices> choices;
    private Usage usage;
    private String id;

    @Data
    public static class Choices {
        private Message message;
        private String finishReason;
        private String logprobs;
    }

    @Data
    public static class Usage {
        @JsonProperty("prompt_tokens")
        private int promptTokens;

        @JsonProperty("completion_tokens")
        private int completionTokens;

        @JsonProperty("total_tokens")
        private int totalTokens;
    }
}
