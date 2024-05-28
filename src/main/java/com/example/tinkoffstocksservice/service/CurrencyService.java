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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
            CompletableFuture<Currency> currencyFuture = instrumentsService.getCurrencyByUid(uid);
            CompletableFuture<List<LastPrice>> priceFuture = marketDataService.getLastPrices(Collections.singleton(uid));
            shortCurrencyResponse = currencyMapper.CurrencyToResponse(currencyFuture.get(), priceFuture.get().get(0));
        } catch (InterruptedException | ExecutionException e) {
            throw new NotFoundException(String.format("Currency by UID(%s) not found",uid));
        }
        return shortCurrencyResponse;
    }

    public ShortCurrencyResponse getConvertedCurrency(String uidCurrency, String uidCurrencyToConvert) {
        InstrumentsService instrumentsService = investApi.getInstrumentsService();
        MarketDataService marketDataService = investApi.getMarketDataService();
        ShortCurrencyResponse shortCurrencyResponse;
        try {
            CompletableFuture<Currency> currencyToConvertFuture = instrumentsService.getCurrencyByUid(uidCurrencyToConvert);
            List<LastPrice> pricesFuture = marketDataService.getLastPrices(Arrays.asList(uidCurrency, uidCurrencyToConvert)).get();
            PriceResponse priceResponse = convertCurrency.convertCurrency(pricesFuture.get(0), pricesFuture.get(1), currencyToConvertFuture.get().getIsoCurrencyName());
            CompletableFuture<Currency> currency = instrumentsService.getCurrencyByUid(uidCurrency);
            shortCurrencyResponse = currencyMapper.CurrencyToResponse(currency.get(), priceResponse);
        } catch (ExecutionException | InterruptedException e) {
            throw new NotFoundException(String.format("Currency can not converted"));
        }
        return shortCurrencyResponse;
    }
}
