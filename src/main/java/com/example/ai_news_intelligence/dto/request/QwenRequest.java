package com.example.ai_news_intelligence.dto.request;

import com.example.ai_news_intelligence.dto.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QwenRequest {
    private String model = "qwen-turbo";
    private List<Message> messages;
}
