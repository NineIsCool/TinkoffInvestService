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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.LastPrice;
import ru.tinkoff.piapi.contract.v1.Share;
import ru.tinkoff.piapi.core.InstrumentsService;
import ru.tinkoff.piapi.core.InvestApi;
import ru.tinkoff.piapi.core.MarketDataService;
import ru.tinkoff.piapi.core.exception.ApiRuntimeException;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Log4j2
public class StockService {
    InvestApi investApi;
    StockMapper stockMapper;
    PriceMapper priceMapper;

    @SneakyThrows
    @Async
    public CompletableFuture<StockResponse> getStockByUid(String uid) {
        InstrumentsService instrumentsService = investApi.getInstrumentsService();
        CompletableFuture<Share> stock;
        try {
            stock = instrumentsService.getShareByUid(uid);
        } catch (ApiRuntimeException e) {
            throw new NotFoundException("Stock by UID not found");
        }
        return CompletableFuture.completedFuture(stockMapper.stockToResponse(stock.get()));
    }

    public PriceResponse getPriceStock(String uid) {
        MarketDataService marketData = investApi.getMarketDataService();
        CompletableFuture<StockResponse> stockResponse = getStockByUid(uid);
        CompletableFuture<List<LastPrice>> lastPrices;
        try {
            lastPrices = marketData.getLastPrices(Collections.singleton(uid));
        } catch (ApiRuntimeException e) {
            throw new NotFoundException("Stock price by UID not found");
        }
        try {
            return priceMapper.priceResponse(stockResponse.get().currency(), lastPrices.get().get(0));
        } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
        }
    }
}
