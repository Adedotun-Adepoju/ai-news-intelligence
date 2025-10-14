package com.example.ai_news_intelligence.service;

import com.example.ai_news_intelligence.dto.youtube.YouTubeSearchResult;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class YouTubeClientService {
    private static final String YOUTUBE_API_BASE_URL = "https://www.googleapis.com/youtube/v3";
    private static final String SEARCH_ENDPOINT = "/search";
    private static final String VIDEO_URL_TEMPLATE = "https://www.youtube.com/watch?v=%s";

    @Value("${youtube.api.key}")
    private String apiKey;

    private WebClient webClient;

    @PostConstruct
    public void initWebClient() {
        this.webClient = WebClient.builder()
                .baseUrl(YOUTUBE_API_BASE_URL)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    /**
     * Search for YouTube videos by query
     * @param query The search query
     * @return List of video URLs matching the search query
     */
    @Cacheable(value = "youtubeSearch", key = "#query")
    public List<String> searchVideos(String query) {
        log.info("Mkaing request....");
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(SEARCH_ENDPOINT)
                        .queryParam("part", "snippet")
                        .queryParam("maxResults", 1)
                        .queryParam("q", query)
                        .queryParam("type", "video")
                        .queryParam("key", apiKey)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(YouTubeSearchResult.class)
                .map(result -> result.getItems().stream()
                        .map(item -> String.format(VIDEO_URL_TEMPLATE, item.getId().getVideoId()))
                        .toList())
                .onErrorResume(e -> {
                    log.error("Error searching YouTube videos: {}", e.getMessage(), e);
                    return Mono.just(Collections.<String>emptyList());
                })
                .block();
    }

    /**
     * Get a single video URL for a search query
     * @param query The search query
     * @return First video URL matching the search query or empty string if none found
     */
    public String getFirstVideoUrl(String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(SEARCH_ENDPOINT)
                        .queryParam("part", "snippet")
                        .queryParam("maxResults", 1)
                        .queryParam("q", query)
                        .queryParam("type", "video")
                        .queryParam("key", apiKey)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(YouTubeSearchResult.class)
                .map(result -> result.getItems().stream()
                        .map(item -> String.format(VIDEO_URL_TEMPLATE, item.getId().getVideoId()))
                        .findFirst()
                        .orElse(""))
                .onErrorResume(e -> {
                    log.error("Error searching YouTube videos: {}", e.getMessage(), e);
                    return Mono.just("");
                })
                .block();
    }
}
