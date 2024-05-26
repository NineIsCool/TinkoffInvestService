package com.example.tinkoffstocksservice.adapter.web.dto.response;

public record ShortCurrencyResponse(
        String name,
        String uid,
        String ticker,
        PriceResponse price
) {}
