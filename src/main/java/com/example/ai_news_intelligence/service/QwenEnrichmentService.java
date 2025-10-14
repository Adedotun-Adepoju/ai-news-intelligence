package com.example.ai_news_intelligence.service;

import com.example.ai_news_intelligence.dto.Article;
import com.example.ai_news_intelligence.dto.Message;
import com.example.ai_news_intelligence.dto.request.QwenRequest;
import com.example.ai_news_intelligence.dto.response.QwenResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QwenEnrichmentService {

    @Value("${qwen.api.key}")
    private String apiKey;

    @Value("${qwen.api.url}")
    private String qwenApiUrl;

    private WebClient webClient;

    @PostConstruct
    public void initWebClient() {
        this.webClient = WebClient.builder()
                .baseUrl(qwenApiUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    /**
     * Enriches a news article with additional insights using Qwen AI
     * @param article The article to enrich
     * @return A Mono containing the enriched article content
     */


//    @Cacheable(value = "qwenEnrichment", key = "#article.getTitle() + #article.getDescription()")
    public QwenResponse enrichArticle(Article article) {
        log.info("Title: {}, description: {}", article.getTitle(), article.getDescription());
        String prompt = """
                     {
                        "summary": "...",                               // A concise 2-sentence summary
                        "tags": ["#tag1", "#tag2"],                     // 3–5 relevant hashtags (use # prefix)
                        "relevance_score": 0.0,                         // A relevance score for African readers between 0.0 and 1.0
                        "images": "...",                                // image search query
                        "videos": "...",                               // video search query
                        "media_justification": "...",                   // short rationale
                        "wikipedia_topic": "...",                      // A 1-sentence Wikipedia snippet about the main topic
                        "search_keyword": "...",
                        "longitude": "..",
                        "latitude": "..",
                        "map_url": "..",
                        "social_sentiment": "..."                      // Analysis of public sentiment from social media. Should back it up with stats (e.g., \"74 percent positive in the last hours, 15 percent neutral, 11 percent negative with high engagement\")
                     }
                     You are an AI journalist assistant. Analyze the following article and fill the fields:
                     Title: %s
                     Body: %s
                """.formatted(
                article.getTitle() != null ? article.getTitle() : "",
                article.getDescription() != null ? article.getDescription() : "");
        Message message = new Message();
        message.setContent(prompt);
        message.setRole("user");

        QwenRequest request = QwenRequest.builder()
                .model("qwen-turbo")
                .messages(List.of(message))
                .build();

        return webClient.post()
                .bodyValue(request)
                .retrieve()
                .bodyToMono(QwenResponse.class)
                .block();
    }
}
