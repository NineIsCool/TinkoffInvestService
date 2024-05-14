package com.example.tinkoffstocksservice.service.mapper;

import com.example.tinkoffstocksservice.adapter.web.dto.response.StockResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.Share;


@RequiredArgsConstructor
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class StockMapper {
    public StockResponse stockToResponse(Share stock){
        return new StockResponse(stock.getName(),stock.getUid(),stock.getTicker(),stock.getCurrency(),stock.getLot());
    }
}
