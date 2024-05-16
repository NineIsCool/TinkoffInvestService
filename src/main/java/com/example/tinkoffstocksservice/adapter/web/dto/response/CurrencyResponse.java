package com.example.tinkoffstocksservice.adapter.web.dto.response;

import java.math.BigDecimal;

public record CurrencyResponse (
        String name,
        String uid,
        String ticker,
        BigDecimal lastPrice
) {}
