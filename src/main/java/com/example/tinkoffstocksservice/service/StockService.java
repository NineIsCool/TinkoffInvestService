package com.example.tinkoffstocksservice.service;

import com.example.tinkoffstocksservice.adapter.web.dto.response.PriceResponse;
import com.example.tinkoffstocksservice.adapter.web.dto.response.ShortInstrumentResponse;
import com.example.tinkoffstocksservice.adapter.web.dto.response.StockResponse;
import com.example.tinkoffstocksservice.adapter.web.errors.NotFoundException;
import com.example.tinkoffstocksservice.service.mapper.InstrumentMapper;
import com.example.tinkoffstocksservice.service.mapper.PriceMapper;
import com.example.tinkoffstocksservice.service.mapper.QuotationConverter;
import com.example.tinkoffstocksservice.service.mapper.StockMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.InstrumentShort;
import ru.tinkoff.piapi.contract.v1.LastPrice;
import ru.tinkoff.piapi.contract.v1.Share;
import ru.tinkoff.piapi.core.InstrumentsService;
import ru.tinkoff.piapi.core.InvestApi;
import ru.tinkoff.piapi.core.MarketDataService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Log4j2
public class StockService {
    InvestApi investApi;
    StockMapper stockMapper;
    InstrumentMapper instrumentMapper;
    QuotationConverter quotationConverter;
    PriceMapper priceMapper;

    @SneakyThrows
    public StockResponse getStockByUid(String uid) {
        InstrumentsService instrumentsService = investApi.getInstrumentsService();
        Share stock;
        try {
            stock = instrumentsService.getShareByUidSync(uid);
        } catch (Exception e) {
            throw new NotFoundException(uid);
        }
        return stockMapper.stockToResponse(stock);
    }

    public List<ShortInstrumentResponse> getStockBySearchParam(String param) {
        InstrumentsService instrumentsService = investApi.getInstrumentsService();
        List<InstrumentShort> instruments = instrumentsService.findInstrumentSync(param);//notfound exception
        List<ShortInstrumentResponse> stockResponses = new ArrayList<>();
        for (InstrumentShort instrument : instruments) {
            if (instrument.getInstrumentType().equals("share")) {
                stockResponses.add(instrumentMapper.InstrumentToResponse(instrument));
            }
        }
        return stockResponses;
    }

    public PriceResponse getPriceStock(String uid) {
        MarketDataService marketData = investApi.getMarketDataService();
        StockResponse stockResponse = getStockByUid(uid);
        List<LastPrice> lastPrices;
        try {
            lastPrices = marketData.getLastPricesSync(Collections.singleton(uid));
        } catch (Exception e) {
            throw new NotFoundException(uid);
        }
        return priceMapper.priceResponse(stockResponse.currency(),lastPrices.get(0));
    }
}
