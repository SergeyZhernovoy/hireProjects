package com.fundcount.profit.business;

import com.fundcount.profit.domain.Currency;
import com.fundcount.profit.domain.CurrencyRate;

import java.time.LocalDate;
import java.util.List;

public interface GetActualCurrencyRate {
    List<CurrencyRate> getActualRate(Currency currency, LocalDate from);
}
