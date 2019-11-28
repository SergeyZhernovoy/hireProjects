package com.fundcount.profit.business.impl;

import com.fundcount.profit.business.CalcProfitService;
import com.fundcount.profit.business.GetActualCurrencyRate;
import com.fundcount.profit.domain.Currency;
import com.fundcount.profit.domain.CurrencyRate;
import com.fundcount.profit.repositories.CurrencyRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalcProfitServiceImpl implements CalcProfitService {
    private final Float spread = 0.5f;
    @Autowired
    private GetActualCurrencyRate getActualCurrencyRate;
    @Autowired
    private CurrencyRepository currencyRepository;
    @Override
    public BigDecimal getCalculateProfit(BigDecimal amount,
                                         Currency currency,
                                         LocalDate from,
                                         LocalDate to) {
        List<CurrencyRate> rateList = getActualCurrencyRate.getActualRate(currency, from)
                .stream()
                .filter( v -> v.getRateDate().equals(from) || v.getRateDate().equals(to))
                .sorted(Comparator.comparing(CurrencyRate::getRateDate))
                .collect(Collectors.toList());

        BigDecimal rubleBefore = amount.multiply(new BigDecimal(rateList.get(0).getRate()))
                                       .multiply(new BigDecimal(spread));
        BigDecimal rubleAfter  = amount.multiply(new BigDecimal(rateList.get(1).getRate()))
                                       .multiply(new BigDecimal(spread));
        return rubleAfter.subtract(rubleBefore);
    }

    @Override
    public List<Currency> getCurrencies() {
        return Lists.newArrayList(currencyRepository.findAll());
    }
}
