package com.example.ai_news_intelligence.controller;

import com.example.ai_news_intelligence.dto.NewsItemDTO;
import com.example.ai_news_intelligence.service.NewsIngestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/news")
public class NewsController {
    private final NewsIngestionService newsIngestionService;

    @GetMapping(value = "/search", produces = "application/json")
    public ResponseEntity<NewsItemDTO> searchNews(@RequestParam("q") String query) {
        return ResponseEntity.ok(newsIngestionService.fetchArticle(query));
    }

    @GetMapping(value = "/{searchParam}", produces = "application/json")
    public ResponseEntity<NewsItemDTO> getArticleByParam(@PathVariable String searchParam) {
        return ResponseEntity.ok(newsIngestionService.getArticleByParam(searchParam));
    }
}
