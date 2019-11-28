package com.fundcount.profit.business.impl;

import com.fundcount.profit.business.GetActualCurrencyRate;
import com.fundcount.profit.domain.Currency;
import com.fundcount.profit.domain.CurrencyRate;
import com.fundcount.profit.repositories.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GetActualCurrencyRateImpl implements GetActualCurrencyRate {

    @Autowired
    private CurrencyRepository repository;

    @Override
    public List<CurrencyRate> getActualRate(Currency currency, LocalDate from) {
        return null;
    }
}
