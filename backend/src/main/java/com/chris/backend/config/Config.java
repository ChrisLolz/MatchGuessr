package com.chris.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config implements WebMvcConfigurer{
    @Value("${api.key}")
    private String API_KEY;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024))
            .baseUrl("http://api.football-data.org/v4/")
            .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader("X-Auth-Token", API_KEY)
            .build();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("Adding CORS mappings...");
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:5173", "https://chrislolz.github.io")
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowedHeaders("*")
            .allowCredentials(true);
    }
}
