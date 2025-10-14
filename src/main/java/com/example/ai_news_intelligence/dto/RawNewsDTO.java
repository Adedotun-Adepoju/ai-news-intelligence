package com.example.ai_news_intelligence.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RawNewsDTO {
   private String status;
   private int totalResults;
   private List<Article> articles;
}
