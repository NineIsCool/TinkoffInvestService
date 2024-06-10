package com.example.tinkoffstocksservice.service.mapper;

import com.example.tinkoffstocksservice.adapter.web.dto.response.PriceResponse;
import com.example.tinkoffstocksservice.service.converter.QuotationConverter;
import com.example.tinkoffstocksservice.service.converter.TimestampConverter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.LastPrice;

@RequiredArgsConstructor
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PriceMapper {
    QuotationConverter quotationConverter;
    TimestampConverter timestampConverter;

    public PriceResponse priceResponse(String currency, LastPrice lastPrice) {
        return new PriceResponse(currency,
                quotationConverter.convertQuotationToBigDecimal(lastPrice.getPrice()),
                timestampConverter.convertTimestampToLocalDateTime(lastPrice.getTime()));
    }
}
