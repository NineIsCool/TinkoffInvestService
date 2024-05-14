package com.example.tinkoffstocksservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "connection-tinkoff")
public class ApiConfig {
    private Boolean isSandBoxMode;
    private Boolean isReadonly;
}
