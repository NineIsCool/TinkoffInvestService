package com.example.tinkoffstocksservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.piapi.core.InvestApi;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(ApiConfig.class)
public class ApplicationConfig {
    private final ApiConfig apiConfig;
    @Bean
    public InvestApi investApi() {
        String token = System.getenv("SSO_TOKEN");
        InvestApi investApi = InvestApi.createReadonly(token);
        return investApi;
    }
}
