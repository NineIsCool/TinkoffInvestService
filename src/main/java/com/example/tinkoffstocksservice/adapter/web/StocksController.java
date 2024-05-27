package com.example.tinkoffstocksservice.adapter.web;

import com.example.tinkoffstocksservice.adapter.web.dto.response.PriceResponse;
import com.example.tinkoffstocksservice.adapter.web.dto.response.StockResponse;
import com.example.tinkoffstocksservice.service.StockService;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Validated
@RequestMapping("api/tinkoff/stocks")
public class StocksController {
    StockService stockService;

    @GetMapping("/stockByUID")
    public StockResponse getStockByUid(@RequestParam("uid") @NotBlank String uid) {
        try {
            return stockService.getStockByUid(uid).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getPriceByUID")
    public PriceResponse getPriceStockByUid(@RequestParam("uid") @NotBlank String uid) {
        return stockService.getPriceStock(uid);
    }
}
