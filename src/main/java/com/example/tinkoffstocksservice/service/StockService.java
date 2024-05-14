package com.example.tinkoffstocksservice.service;

import com.example.tinkoffstocksservice.adapter.web.dto.response.ShortInstrumentResponse;
import com.example.tinkoffstocksservice.adapter.web.dto.response.StockResponse;
import com.example.tinkoffstocksservice.service.mapper.InstrumentMapper;
import com.example.tinkoffstocksservice.service.mapper.QuotationConverter;
import com.example.tinkoffstocksservice.service.mapper.StockMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.InstrumentShort;
import ru.tinkoff.piapi.contract.v1.LastPrice;
import ru.tinkoff.piapi.contract.v1.Share;
import ru.tinkoff.piapi.core.InstrumentsService;
import ru.tinkoff.piapi.core.InvestApi;
import ru.tinkoff.piapi.core.MarketDataService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class StockService {
    InvestApi investApi;
    StockMapper stockMapper;
    InstrumentMapper instrumentMapper;
    QuotationConverter quotationConverter;

    @SneakyThrows
    public StockResponse getStockByFigi(String figi) {
        InstrumentsService instrumentsService = investApi.getInstrumentsService();
        Share stock = instrumentsService.getShareByFigiSync(figi);
        return stockMapper.StockToResponse(stock);
    }
    public List<ShortInstrumentResponse> getStockBySearchParam(String param){
        InstrumentsService instrumentsService = investApi.getInstrumentsService();
        List<InstrumentShort> instruments = instrumentsService.findInstrumentSync(param);
        List<ShortInstrumentResponse> stockResponses = new ArrayList<>();
        for (InstrumentShort instrument:instruments) {
            if (instrument.getInstrumentType().equals("share")){
                stockResponses.add(instrumentMapper.InstrumentToResponse(instrument));
            }
        }
        return stockResponses;
    }

    public String getPriceStock(String figi){
        MarketDataService marketData = investApi.getMarketDataService();
        List<String> figis = new ArrayList<>();
        figis.add(figi);
        List<LastPrice> s =  marketData.getLastPricesSync(figis);
        return quotationConverter.convertQuotationToBigDecimal(s.get(0).getPrice()).toString();
    }
}
