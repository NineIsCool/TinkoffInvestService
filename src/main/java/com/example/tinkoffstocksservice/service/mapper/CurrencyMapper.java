package com.example.tinkoffstocksservice.service.mapper;

import com.example.tinkoffstocksservice.adapter.web.dto.response.PriceResponse;
import com.example.tinkoffstocksservice.adapter.web.dto.response.ShortCurrencyResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.Currency;
import ru.tinkoff.piapi.contract.v1.CurrencyResponse;
import ru.tinkoff.piapi.contract.v1.LastPrice;
import ru.tinkoff.piapi.contract.v1.LastPriceOrBuilder;

@RequiredArgsConstructor
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CurrencyMapper {
    PriceMapper priceMapper;
    public ShortCurrencyResponse CurrencyToResponse(Currency currency, LastPrice lastPrice){
        PriceResponse priceResponse = priceMapper.priceResponse(currency.getCurrency(),lastPrice);
        return new ShortCurrencyResponse(currency.getName(),currency.getUid(),currency.getTicker(),priceResponse);
    }
    public ShortCurrencyResponse CurrencyToResponse(Currency currency, PriceResponse priceResponse){
        return new ShortCurrencyResponse(currency.getName(),currency.getUid(),currency.getTicker(),priceResponse);
    }
}
