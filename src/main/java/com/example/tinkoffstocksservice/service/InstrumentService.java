package com.example.tinkoffstocksservice.service;

import com.example.tinkoffstocksservice.adapter.web.dto.response.ShortInstrumentResponse;
import com.example.tinkoffstocksservice.service.mapper.InstrumentMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.InstrumentShort;
import ru.tinkoff.piapi.core.InstrumentsService;
import ru.tinkoff.piapi.core.InvestApi;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class InstrumentService {
    InvestApi investApi;
    InstrumentMapper instrumentMapper;

    public List<ShortInstrumentResponse> getStockBySearchParam(String param, String type) {
        InstrumentsService instrumentsService = investApi.getInstrumentsService();
        List<InstrumentShort> instruments = instrumentsService.findInstrumentSync(param);
        List<ShortInstrumentResponse> stockResponses = new ArrayList<>();
        for (InstrumentShort instrument : instruments) {
            if (instrument.getInstrumentType().equals(type)) {
                stockResponses.add(instrumentMapper.InstrumentToResponse(instrument));
            }
        }
        return stockResponses;
    }
}
