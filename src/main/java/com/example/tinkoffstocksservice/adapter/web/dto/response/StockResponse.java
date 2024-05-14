package com.example.tinkoffstocksservice.adapter.web.dto.response;

import java.math.BigDecimal;

public record StockResponse (
        String figi,
        String name,
        String ticker,
        String currency,
        int lot,
        String exchange
){}
