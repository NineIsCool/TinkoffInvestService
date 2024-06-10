package com.example.tinkoffstocksservice.adapter.web.error;

import com.example.tinkoffstocksservice.adapter.web.error.common.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotFoundException extends CommonException {

    public NotFoundException(String message) {
        super(ErrorCode.NOT_FOUND_EXCEPTION, HttpStatus.NOT_FOUND, message);
    }
}
