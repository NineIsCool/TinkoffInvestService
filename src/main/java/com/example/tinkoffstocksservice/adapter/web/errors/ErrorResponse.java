package com.example.tinkoffstocksservice.adapter.web.errors;

import com.example.tinkoffstocksservice.adapter.web.errors.common.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(ErrorCode code, String field, String message) {
    public ErrorResponse(ErrorCode code, String message) {
        this(code, null, message);
    }
}
