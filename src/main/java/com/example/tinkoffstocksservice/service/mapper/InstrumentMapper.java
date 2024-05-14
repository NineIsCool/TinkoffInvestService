package com.example.tinkoffstocksservice.service.mapper;

import com.example.tinkoffstocksservice.adapter.web.dto.response.ShortInstrumentResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.InstrumentShort;

@RequiredArgsConstructor
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class InstrumentMapper {
    MoneyValueConverter converter;
    public ShortInstrumentResponse InstrumentToResponse(InstrumentShort instrument){
        return new ShortInstrumentResponse(instrument.getFigi(),instrument.getName(),instrument.getTicker());
    }
}
