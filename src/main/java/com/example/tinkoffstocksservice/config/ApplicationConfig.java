package com.example.tinkoffstocksservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import ru.tinkoff.piapi.core.InvestApi;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(ApiConfig.class)
@Log4j2
@EnableAsync
public class ApplicationConfig {
    private final ApiConfig apiConfig;

    @Bean
    public InvestApi investApi() {
        String token = System.getenv("SSO_TOKEN");
        InvestApi investApi;
        if (apiConfig.getIsSandBoxMode()) {
            investApi = InvestApi.createSandbox(token);
            log.info("connection to tinkoff api success! Mode: SandBox");
        } else {
            if (apiConfig.getIsReadonly()) {
                investApi = InvestApi.createReadonly(token);
                log.info("connection to tinkoff api success! Mode: Readonly");
            } else {
                investApi = InvestApi.create(token);
                log.info("connection to tinkoff api success! Mode: Default");
            }
        }
        return investApi;
    }
}
