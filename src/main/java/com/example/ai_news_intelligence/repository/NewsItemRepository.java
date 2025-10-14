package com.example.ai_news_intelligence.repository;

import com.example.ai_news_intelligence.model.NewsItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NewsItemRepository extends JpaRepository<NewsItem, Long> {
    // Find by query parameter
    Optional<NewsItem> findFirstByQueryParamOrderByIngestedAtDesc(String queryParam);
}
