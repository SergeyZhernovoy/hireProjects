package com.fundcount.profit.configurations;


import com.fundcount.profit.domain.Currency;
import com.fundcount.profit.domain.CurrencyRate;
import com.fundcount.profit.repositories.CurrencyRateRepository;
import com.fundcount.profit.repositories.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Configuration
public class InitialConfiguration {

    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private CurrencyRateRepository currencyRateRepository;

    @Bean
    @Transactional
    public CommandLineRunner getRunner() {
        return args -> {
            Currency usd = new Currency("USD",840);
            CurrencyRate usdRate1 = new CurrencyRate(LocalDate.parse("2019-01-01"), 65.0f);
            CurrencyRate usdRate2 = new CurrencyRate(LocalDate.parse("2019-02-01"), 66.0f);
            usd = currencyRepository.save(usd);
            usdRate1.setCurrency(usd);
            usdRate2.setCurrency(usd);
            currencyRateRepository.save(usdRate1);
            currencyRateRepository.save(usdRate2);
        };
    }
}
