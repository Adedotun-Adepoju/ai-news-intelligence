package com.example.ai_news_intelligence.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {
    private final HttpStatus status;

    public BaseException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
