package com.example.tinkoffstocksservice.adapter.web.dto.response;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public record PriceResponse(
        String currency,
        BigDecimal lastPrice,
        LocalDateTime timeLastPrice) {
}
