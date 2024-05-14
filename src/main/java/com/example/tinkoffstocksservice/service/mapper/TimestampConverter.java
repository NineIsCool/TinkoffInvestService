package com.example.tinkoffstocksservice.service.mapper;

import com.google.protobuf.Timestamp;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class TimestampConverter {
    public LocalDateTime convertTimestampToLocalDateTime(Timestamp timestamp) {
        Instant instant = Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
        return LocalDateTime.ofInstant(instant, ZoneId.of("UTC" + ZonedDateTime.now().getOffset()));
    }
}
