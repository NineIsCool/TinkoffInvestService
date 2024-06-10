package com.example.tinkoffstocksservice.adapter.web.error;

import com.example.tinkoffstocksservice.adapter.web.error.common.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(ErrorCode code, String field, String message) {
    public ErrorResponse(ErrorCode code, String message) {
        this(code, null, message);
    }
}
