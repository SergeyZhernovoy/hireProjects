package com.fundcount.profit.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class CurrencyRate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private LocalDate rateDate;
    private Float rate;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "currency")
    private Currency currency;

    public CurrencyRate(LocalDate date, Float rate) {
        this.rateDate = date;
        this.rate = rate;
    }
    public CurrencyRate() {

    }
}
