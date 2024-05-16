package com.example.tinkoffstocksservice.service.converter;

import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.MoneyValue;

import java.math.BigDecimal;
@Component
public class MoneyValueConverter {
    public BigDecimal convertMoneyValueToBigDecimal(MoneyValue moneyValue){
        if (moneyValue.getUnits() == 0 && moneyValue.getNano() == 0){
            return BigDecimal.ZERO;
        }else {
            return BigDecimal.valueOf(moneyValue.getUnits()).
                    add(BigDecimal.valueOf(moneyValue.getNano(), 9));
        }
    }
}
