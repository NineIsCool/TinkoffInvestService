package com.example.tinkoffstocksservice.adapter.web.dto.response;

public record ShortInstrumentResponse(
        String name,
        String uid,
        String ticker
) {
}
