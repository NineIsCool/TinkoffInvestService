package com.example.tinkoffstocksservice.service;

import com.example.tinkoffstocksservice.adapter.web.dto.response.PriceResponse;
import com.example.tinkoffstocksservice.adapter.web.dto.response.ShortCurrencyResponse;
import com.example.tinkoffstocksservice.adapter.web.errors.NotFoundException;
import com.example.tinkoffstocksservice.service.converter.ConvertCurrency;
import com.example.tinkoffstocksservice.service.mapper.CurrencyMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.Currency;
import ru.tinkoff.piapi.contract.v1.LastPrice;
import ru.tinkoff.piapi.core.InstrumentsService;
import ru.tinkoff.piapi.core.InvestApi;
import ru.tinkoff.piapi.core.MarketDataService;
import ru.tinkoff.piapi.core.exception.ApiRuntimeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Log4j2
public class CurrencyService {
    InvestApi investApi;
    CurrencyMapper currencyMapper;
    ConvertCurrency convertCurrency;

    public ShortCurrencyResponse getCurrencyByUID(String uid) {
        InstrumentsService instrumentsService = investApi.getInstrumentsService();
        MarketDataService marketDataService = investApi.getMarketDataService();
        ShortCurrencyResponse shortCurrencyResponse;
        try {
            Currency currency = instrumentsService.getCurrencyByUidSync(uid);
            LastPrice price = marketDataService.getLastPricesSync(Collections.singleton(uid)).get(0);
            shortCurrencyResponse = currencyMapper.CurrencyToResponse(currency, price);
        } catch (ApiRuntimeException e) {
            throw new NotFoundException("Currency by UID not found");
        }
        return shortCurrencyResponse;
    }

    public ShortCurrencyResponse getConvertedCurrency(String uidCurrency, String uidConvert) {
        InstrumentsService instrumentsService = investApi.getInstrumentsService();
        MarketDataService marketDataService = investApi.getMarketDataService();
        ShortCurrencyResponse shortCurrencyResponse;
        try {
            Currency currencyConvert = instrumentsService.getCurrencyByUidSync(uidConvert);
            List<LastPrice> priceConvert = marketDataService.getLastPricesSync(Arrays.asList(uidCurrency, uidConvert));
            PriceResponse priceResponse = convertCurrency.convertCurrency(priceConvert.get(0),priceConvert.get(1),currencyConvert.getIsoCurrencyName());
            Currency currency = instrumentsService.getCurrencyByUidSync(uidCurrency);
            shortCurrencyResponse = currencyMapper.CurrencyToResponse(currency,priceResponse);
        } catch (ApiRuntimeException e) {
            throw new NotFoundException("Currency by UID not found");
        }
        return shortCurrencyResponse;
    }
}
