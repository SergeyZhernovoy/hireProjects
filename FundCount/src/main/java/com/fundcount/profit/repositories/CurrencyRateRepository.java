package com.fundcount.profit.repositories;

import com.fundcount.profit.domain.CurrencyRate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRateRepository extends CrudRepository<CurrencyRate, Long> {
}
