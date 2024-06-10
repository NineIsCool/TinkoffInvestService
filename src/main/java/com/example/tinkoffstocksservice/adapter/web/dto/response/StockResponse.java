package com.example.tinkoffstocksservice.adapter.web.dto.response;

public record StockResponse(
        String name,
        String uid,
        String ticker,
        String currency,
        int lot
) {
}
