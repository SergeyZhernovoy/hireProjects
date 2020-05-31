package ru.crpt.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.crpt.cache.EvaluateAtCache;
import ru.crpt.model.Document;

@Configuration
public class ApiConfig {
    @Bean
    public EvaluateAtCache<String, Document> evaluatingCache() {
        return new EvaluateAtCache<>();
    }
}
