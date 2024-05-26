package com.example.tinkoffstocksservice.service;

import com.example.tinkoffstocksservice.adapter.web.dto.response.ShortCurrencyResponse;
import com.example.tinkoffstocksservice.adapter.web.errors.NotFoundException;
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

import java.util.Collections;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Log4j2
public class CurrencyService {
    InvestApi investApi;
    CurrencyMapper currencyMapper;
    public ShortCurrencyResponse getCurrencyByUID(String uid) {
        InstrumentsService instrumentsService= investApi.getInstrumentsService();
        MarketDataService marketDataService= investApi.getMarketDataService();
        ShortCurrencyResponse shortCurrencyResponse;
        try {
            Currency currency = instrumentsService.getCurrencyByUidSync(uid);
            LastPrice price =  marketDataService.getLastPricesSync(Collections.singleton(uid)).get(0);
            shortCurrencyResponse=currencyMapper.CurrencyToResponse(currency,price);
        }catch (ApiRuntimeException e){
            throw new NotFoundException("Currency by UID not found");
        }
        return shortCurrencyResponse;
    }
}
