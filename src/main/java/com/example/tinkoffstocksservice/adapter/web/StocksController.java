package com.example.tinkoffstocksservice.adapter.web;

import com.example.tinkoffstocksservice.adapter.web.dto.response.ShortInstrumentResponse;
import com.example.tinkoffstocksservice.adapter.web.dto.response.StockResponse;
import com.example.tinkoffstocksservice.service.StockService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("api/stocks/tinkoff")
public class StocksController {
    StockService stockService;
    @GetMapping("/stockByNameAndClassCode")
    public StockResponse getStockByFigi(@RequestParam("figi") String figi){
        return stockService.getStockByFigi(figi);
    }

    @GetMapping("/findInstrument")
    public List<ShortInstrumentResponse> findStockBySearchParameter(@RequestParam("param") String param){
        return stockService.getStockBySearchParam(param);
    }

    @GetMapping("/getPriceByFigi")
    public String getPriceByFigi(@RequestParam("figi") String figi){
        return stockService.getPriceStock(figi);
    }
}
