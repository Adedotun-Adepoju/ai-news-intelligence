package com.example.ai_news_intelligence.service;

import com.example.ai_news_intelligence.dto.NewsItemDTO;
import com.example.ai_news_intelligence.dto.response.QwenResponse;
import com.example.ai_news_intelligence.dto.RawNewsDTO;
import com.example.ai_news_intelligence.exception.BaseException;
import com.example.ai_news_intelligence.model.NewsItem;
import com.example.ai_news_intelligence.repository.NewsItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsIngestionService {
    private final NewsAssemblerService newsAssemblerService;
    private final NewsItemRepository newsItemRepository;

    @Value("${news.api.key}")
    private String apiKey;

    @Value("${news.api.url}")
    private String newsBaseUrl;

    private WebClient client;

    @PostConstruct
    public  void initWebClient() {
        this.client = WebClient.builder()
                .baseUrl(newsBaseUrl)
                .defaultHeader("X-Api-Key", apiKey)
                .build();
    }

    /**
     * Fetches news articles from Bing News API based on a search query
     * @param query The search query string
     * @return List of NewsItem objects
     */
    public NewsItemDTO fetchArticle(String query) {
        Optional<NewsItem> optionalNewsItem = newsItemRepository.findFirstByQueryParamOrderByIngestedAtDesc(query);

        if (optionalNewsItem.isPresent()) {
            log.info("Query param found, returning result from DB");
            return newsAssemblerService.convertToDTO(optionalNewsItem.get());
        }

        log.info("Making request to get news by query");
        RawNewsDTO newsDTO = client.get()
                .uri(uriBuilder -> uriBuilder.path("/everything")
                        .queryParam("q", query)
                        .queryParam("language", "en")
                        .queryParam("sortBy", "publishedAt")
                        .queryParam("pageSize", 1)
                        .queryParam("page", 1)
                        .build())
                .retrieve()
                .bodyToMono(RawNewsDTO.class)
                .block();

        // make request to QwenEnrichmentService
        if (newsDTO == null) {
            log.info("No news found");
            throw new BaseException(HttpStatus.BAD_REQUEST, "No news found for the specified keyword");
        }

        log.info("Attempting to assemble news....");

        return newsAssemblerService.assemble(newsDTO, query);
    }

    public NewsItemDTO getArticleByParam(String param) {
        Optional<NewsItem> optionalNewsItem = newsItemRepository.findFirstByQueryParamOrderByIngestedAtDesc(param);

        if (optionalNewsItem.isPresent()) {
            log.info("Query param found, returning result from DB");
            return newsAssemblerService.convertToDTO(optionalNewsItem.get());
        }

        throw new BaseException(HttpStatus.BAD_REQUEST, "No news found for the specified keyword, please call the /search endpoint to trigger calls to fetch the latest news");
    }
}
