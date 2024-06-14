package com.example.tinkoffstocksservice.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "connection-tinkoff")
public class ApiConfig {
    private Boolean isSandBoxMode;
    private Boolean isReadonly;
}
