package com.example.tinkoffstocksservice.service.converter;

import com.example.tinkoffstocksservice.adapter.web.dto.response.PriceResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.LastPrice;
import ru.tinkoff.piapi.contract.v1.Quotation;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ConvertCurrency {
    QuotationConverter quotationConverter;
    TimestampConverter timestampConverter;
    public PriceResponse convertCurrency(LastPrice price, LastPrice priceTo, String currencyTo){
        BigDecimal lastPrice = quotationConverter.convertQuotationToBigDecimal(price.getPrice());
        BigDecimal priceToConvert = quotationConverter.convertQuotationToBigDecimal(priceTo.getPrice());
        return new PriceResponse(currencyTo,lastPrice.divide(priceToConvert,5, RoundingMode.UP),
                timestampConverter.convertTimestampToLocalDateTime(price.getTime()));
    }

}
