package com.example.tinkoffstocksservice.adapter.web;

import com.example.tinkoffstocksservice.adapter.web.dto.response.ShortInstrumentResponse;
import com.example.tinkoffstocksservice.adapter.web.validation.InstrumentTypeConstraint;
import com.example.tinkoffstocksservice.service.InstrumentService;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Validated
@RequestMapping("api/tinkoff/instrument")
public class InstrumentController {
    InstrumentService instrumentService;

    @GetMapping("/findInstrument")
    public List<ShortInstrumentResponse> findStockBySearchParameter(@RequestParam(value = "param") @NotBlank String param,
                                                                    @RequestParam(value = "type") @NotBlank @InstrumentTypeConstraint String type) {
        return instrumentService.getStockBySearchParam(param, type);
    }
}
