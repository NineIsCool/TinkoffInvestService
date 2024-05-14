package com.example.tinkoffstocksservice.adapter.web.errors;

import com.example.tinkoffstocksservice.adapter.web.errors.common.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotFoundException extends RuntimeException {

    ErrorCode code = ErrorCode.NOT_FOUND;

    public NotFoundException(String param) {
        super("Not found by " + param);
    }
}
