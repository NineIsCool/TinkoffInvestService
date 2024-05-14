package com.example.tinkoffstocksservice.adapter.web.dto.response;

import java.math.BigDecimal;

public record StockResponse (
        String name,
        String uid,
        String ticker,
        String currency,
        int lot
){}
