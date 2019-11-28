package com.fundcount.profit.business;

import com.fundcount.profit.domain.Currency;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public interface CalcProfitService {
    BigDecimal getCalculateProfit(BigDecimal amount, Currency currency, LocalDate from, LocalDate to);

    List<Currency> getCurrencies();
}
