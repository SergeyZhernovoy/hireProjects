package com.fundcount.profit.controllers;

import com.fundcount.profit.business.CalcProfitService;
import com.fundcount.profit.domain.Currency;
import com.fundcount.profit.domain.Profit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
public class CalcController {
    @Autowired
    private CalcProfitService calcProfitService;


    @RequestMapping(value = "/currency", method = RequestMethod.GET)
    public List<Currency> getCurrencies() {
        return calcProfitService.getCurrencies();
    }


    @RequestMapping(value = "/profit", method = RequestMethod.GET)
    public Profit getProfit() {
        BigDecimal amountProfit =  calcProfitService.getCalculateProfit(new BigDecimal(100f),
                new Currency("USD", 640),
                LocalDate.parse("2019-01-01"),
                LocalDate.now());
        Profit profit = new Profit();
        profit.setProfit(amountProfit);
        return profit;
    }
}
