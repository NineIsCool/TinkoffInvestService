package com.example.tinkoffstocksservice.service;

import com.example.tinkoffstocksservice.adapter.web.dto.response.PriceResponse;
import com.example.tinkoffstocksservice.adapter.web.dto.response.StockResponse;
import com.example.tinkoffstocksservice.adapter.web.errors.NotFoundException;
import com.example.tinkoffstocksservice.service.mapper.PriceMapper;
import com.example.tinkoffstocksservice.service.mapper.StockMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.LastPrice;
import ru.tinkoff.piapi.contract.v1.Share;
import ru.tinkoff.piapi.core.InstrumentsService;
import ru.tinkoff.piapi.core.InvestApi;
import ru.tinkoff.piapi.core.MarketDataService;
import ru.tinkoff.piapi.core.exception.ApiRuntimeException;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Log4j2
public class StockService {
    InvestApi investApi;
    StockMapper stockMapper;
    PriceMapper priceMapper;

    @SneakyThrows
    public StockResponse getStockByUid(String uid) {
        InstrumentsService instrumentsService = investApi.getInstrumentsService();
        Share stock;
        try {
            stock = instrumentsService.getShareByUidSync(uid);
        } catch (ApiRuntimeException e) {
            throw new NotFoundException(uid);
        }
        return stockMapper.stockToResponse(stock);
    }

    public PriceResponse getPriceStock(String uid) {
        MarketDataService marketData = investApi.getMarketDataService();
        StockResponse stockResponse = getStockByUid(uid);
        List<LastPrice> lastPrices;
        try {
            lastPrices = marketData.getLastPricesSync(Collections.singleton(uid));
        } catch (ApiRuntimeException e) {
            throw new NotFoundException(uid);
        }
        return priceMapper.priceResponse(stockResponse.currency(), lastPrices.get(0));
    }
}
