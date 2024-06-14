package com.example.tinkoffstocksservice.adapter.web.error;

import com.example.tinkoffstocksservice.adapter.web.error.common.ErrorCode;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;


@Builder
@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommonException extends RuntimeException {
    ErrorCode errorCode;
    HttpStatus httpStatus;
    String message;
}
