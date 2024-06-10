package com.example.tinkoffstocksservice.service;

import com.example.tinkoffstocksservice.adapter.web.dto.response.PriceResponse;
import com.example.tinkoffstocksservice.adapter.web.dto.response.StockResponse;
import com.example.tinkoffstocksservice.adapter.web.error.NotFoundException;
import com.example.tinkoffstocksservice.service.mapper.PriceMapper;
import com.example.tinkoffstocksservice.service.mapper.StockMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.LastPrice;
import ru.tinkoff.piapi.contract.v1.Share;
import ru.tinkoff.piapi.core.InstrumentsService;
import ru.tinkoff.piapi.core.InvestApi;
import ru.tinkoff.piapi.core.MarketDataService;

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


    public StockResponse getStockByUid(String uid) {
        InstrumentsService instrumentsService = investApi.getInstrumentsService();
        CompletableFuture<Share> stock = instrumentsService.getShareByUid(uid);
        try {
            return stockMapper.stockToResponse(stock.get());
        } catch (InterruptedException | ExecutionException e) {
            log.info(String.format("Stock by UID(%s) not found", uid));
            throw new NotFoundException(String.format("Stock by UID(%s) not found", uid));
        }
    }

    public PriceResponse getPriceStock(String uid) {
        MarketDataService marketData = investApi.getMarketDataService();
        CompletableFuture<StockResponse> stockResponse = CompletableFuture.supplyAsync(() -> getStockByUid(uid));
        CompletableFuture<List<LastPrice>> lastPrices = marketData.getLastPrices(Collections.singleton(uid));
        try {
            return priceMapper.priceResponse(stockResponse.get().currency(), lastPrices.get().get(0));
        } catch (InterruptedException | ExecutionException e) {
            log.info(String.format("Stock by UID(%s) not found", uid));
            throw new NotFoundException(String.format("Stock by UID(%s) not found", uid));
        }
    }
}
