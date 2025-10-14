package com.example.ai_news_intelligence.service;

import com.example.ai_news_intelligence.dto.Article;
import com.example.ai_news_intelligence.dto.NewsItemDTO;
import com.example.ai_news_intelligence.dto.RawNewsDTO;
import com.example.ai_news_intelligence.dto.meta.AiResponseDTO;
import com.example.ai_news_intelligence.dto.meta.Context;
import com.example.ai_news_intelligence.dto.meta.Geo;
import com.example.ai_news_intelligence.dto.meta.Media;
import com.example.ai_news_intelligence.dto.response.QwenResponse;
import com.example.ai_news_intelligence.model.NewsItem;
import com.example.ai_news_intelligence.repository.NewsItemRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class NewsAssemblerService {
    private final NewsItemRepository newsItemRepository;
    private final QwenEnrichmentService qwenEnrichmentService;
    private final YouTubeClientService youTubeClientService;

    private final ObjectMapper objectMapper;

    public NewsItemDTO assemble(RawNewsDTO newsDTO, String query) {
        Article article = newsDTO.getArticles().getFirst();
        LocalDateTime now = LocalDateTime.now();

        NewsItemDTO newsItemDTO = new NewsItemDTO();

        newsItemDTO.setTitle(article.getTitle());
        newsItemDTO.setBody(article.getContent());
        newsItemDTO.setSourceUrl(article.getUrl());
        newsItemDTO.setPublishedAt(article.getPublishedAt());
        newsItemDTO.setIngestedAt(now.toString());

        Media media = new Media();
        media.setFeaturedImageUrl(article.getUrlToImage());

        QwenResponse qwenResponse = qwenEnrichmentService.enrichArticle(newsDTO.getArticles().getFirst());
        log.info("Enrichment done");
        String content = qwenResponse.getChoices().getFirst().getMessage().getContent();

        log.info("See content: {}", content);
        AiResponseDTO aiResponseDTO;
        try {
            aiResponseDTO = objectMapper.readValue(content, AiResponseDTO.class);
        } catch (JsonProcessingException e) {
            log.error("Error parsing AI response: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to process AI response", e);
        }

        // Map AiResponseDTO fields to NewsItem
        newsItemDTO.setSummary(aiResponseDTO.getSummary());
        newsItemDTO.setTags(aiResponseDTO.getTags());
        newsItemDTO.setRelevanceScore(aiResponseDTO.getRelevanceScore());
        media.setMediaJustification(aiResponseDTO.getMediaJustification());

        Geo geo = new Geo();
        geo.setLat(aiResponseDTO.getLatitude());
        geo.setLng(aiResponseDTO.getLongitude());
        geo.setMapUrl(aiResponseDTO.getMapUrl());

        Context context = Context.builder()
                .wikipediaSnippet(aiResponseDTO.getWikipediaTopic())
                .socialSentiment(aiResponseDTO.getSocialSentiment())
                .searchTrend(aiResponseDTO.getSearchKeyword())
                .geo(geo)
                .build();

        newsItemDTO.setContext(context);

        String videoURL = null;

        try {
            log.info("Attempting to get video url");
            videoURL = youTubeClientService.getFirstVideoUrl(aiResponseDTO.getVideos());
        } catch (Exception e) {
            log.info("An exception occurred when trying to get vide url, see message: {}", e.getMessage(), e);
        }
        media.setRelatedVideoUrl(videoURL);
        newsItemDTO.setMedia(media);

        CompletableFuture.runAsync(() -> {
            saveNewsItem(newsItemDTO, query);
        });
        return newsItemDTO;
    }

    public NewsItemDTO convertToDTO(NewsItem newsItem) {
        if (newsItem == null) {
            return null;
        }
        
        NewsItemDTO dto = new NewsItemDTO();
        dto.setId(String.valueOf(newsItem.getId()));
        dto.setTitle(newsItem.getTitle());
        dto.setBody(newsItem.getBody());
        dto.setSummary(newsItem.getSummary());
        
        // Convert comma-separated tags to List
        if (newsItem.getTags() != null && !newsItem.getTags().trim().isEmpty()) {
            dto.setTags(List.of(newsItem.getTags().split(",")));
        } else {
            dto.setTags(List.of());
        }
        
        dto.setRelevanceScore(newsItem.getRelevanceScore());
        dto.setSourceUrl(newsItem.getSourceUrl());
        dto.setPublisher(newsItem.getPublisher());
        dto.setPublishedAt(newsItem.getPublishedAt());
        dto.setIngestedAt(newsItem.getIngestedAt());
        
        // Set media
        Media media = new Media();
        media.setFeaturedImageUrl(newsItem.getImageUrl());
        media.setRelatedVideoUrl(newsItem.getVideoUrl());
        media.setMediaJustification(newsItem.getMediaJustification());
        dto.setMedia(media);

        Geo geo = new Geo();
        geo.setLat(newsItem.getGeoLat());
        geo.setLng(newsItem.getGeoLng());
        geo.setMapUrl(newsItem.getGeoMapUrl());

        Context context = Context.builder()
                .wikipediaSnippet(newsItem.getWikiSnippet())
                .socialSentiment(newsItem.getSocialSentiment())
                .searchTrend(newsItem.getSearchTrend())
                .geo(geo)
                .build();
        dto.setContext(context);
        return dto;
    }
    
    private NewsItem saveNewsItem(NewsItemDTO newsItemDTO, String queryParam) {
        log.info("Saving news item.....");
        NewsItem newsItem = new NewsItem();
        newsItem.setTitle(newsItemDTO.getTitle());
        newsItem.setBody(newsItemDTO.getBody());
        
        // Convert List of tags to comma-separated string
        if (newsItemDTO.getTags() != null) {
            String tagsString = String.join(",", newsItemDTO.getTags());
            newsItem.setTags(tagsString);
        } else {
            newsItem.setTags("");
        }
        
        newsItem.setRelevanceScore(newsItemDTO.getRelevanceScore());
        newsItem.setSummary(newsItemDTO.getSummary());
        newsItem.setPublisher(newsItemDTO.getPublisher());
        newsItem.setSourceUrl(newsItemDTO.getSourceUrl());
        newsItem.setPublishedAt(newsItemDTO.getPublishedAt());
        newsItem.setIngestedAt(LocalDateTime.now().toString());

        newsItem.setImageUrl(newsItemDTO.getMedia().getFeaturedImageUrl());
        newsItem.setVideoUrl(newsItemDTO.getMedia().getRelatedVideoUrl());
        newsItem.setMediaJustification(newsItemDTO.getMedia().getMediaJustification());

        newsItem.setGeoLat(newsItemDTO.getContext().getGeo().getLat());
        newsItem.setGeoLng(newsItemDTO.getContext().getGeo().getLng());
        newsItem.setGeoMapUrl(newsItemDTO.getContext().getGeo().getMapUrl());

        newsItem.setWikiSnippet(newsItemDTO.getContext().getWikipediaSnippet());
        newsItem.setSocialSentiment(newsItemDTO.getContext().getSocialSentiment());
        newsItem.setSearchTrend(newsItemDTO.getContext().getSearchTrend());

        newsItem.setQueryParam(queryParam);
        
        return newsItemRepository.save(newsItem);
    }
}
