package com.example.tinkoffstocksservice.service.converter;

import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.Quotation;

import java.math.BigDecimal;

@Component
public class QuotationConverter {
    public BigDecimal convertQuotationToBigDecimal(Quotation quotation) {
        if (quotation.getUnits() == 0 && quotation.getNano() == 0) {
            return BigDecimal.ZERO;
        } else {
            return BigDecimal.valueOf(quotation.getUnits())
                    .add(BigDecimal.valueOf(quotation.getNano(), 9));
        }
    }
}
