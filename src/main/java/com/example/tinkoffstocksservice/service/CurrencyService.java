package com.example.tinkoffstocksservice.service;

import com.example.tinkoffstocksservice.adapter.web.dto.response.CurrencyResponse;
import com.example.tinkoffstocksservice.adapter.web.errors.NotFoundException;
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

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Log4j2
public class CurrencyService {
    InvestApi investApi;
    public CurrencyResponse getCurrencyByUID(String uid) {
        InstrumentsService instrumentsService= investApi.getInstrumentsService();
        MarketDataService marketDataService= investApi.getMarketDataService();
        try {
            Currency currency = instrumentsService.getCurrencyByUidSync(uid);
            List<LastPrice> price =  marketDataService.getLastPricesSync(Collections.singleton(uid));

        }catch (Exception e){
            throw new NotFoundException("UID not found");
        }
    }
}
