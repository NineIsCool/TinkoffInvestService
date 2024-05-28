package com.example.tinkoffstocksservice.adapter.web;

import com.example.tinkoffstocksservice.adapter.web.dto.response.ShortCurrencyResponse;
import com.example.tinkoffstocksservice.service.CurrencyService;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("api/tinkoff/currency")
@Validated
public class CurrencyController {
    CurrencyService currencyService;
    @GetMapping("/currency")
    public ShortCurrencyResponse getCurrencyByUID(@RequestParam("uid") @NotBlank String uid){
        return currencyService.getCurrencyByUID(uid);
    }

    @GetMapping("/convert")
    public ShortCurrencyResponse convertCurrency(@RequestParam("uidCurrency") @NotBlank String uidCurrency,
                                                 @RequestParam("uidConvert") @NotBlank String uidConvert){
        return currencyService.getConvertedCurrency(uidCurrency,uidConvert);
    }
}
