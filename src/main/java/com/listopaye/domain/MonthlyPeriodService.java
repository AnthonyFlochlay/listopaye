package com.listopaye.domain;

import com.listopaye.domain.spi.MonthlyPeriodRepository;
import com.listopaye.domain.spi.stub.MonthlyPeriodId;
import org.springframework.stereotype.Service;

import java.time.Month;

@Service
public class MonthlyPeriodService {

    private final MonthlyPeriodRepository monthlyPeriodRepository;

    public MonthlyPeriodService(MonthlyPeriodRepository monthlyPeriodRepository) {
        this.monthlyPeriodRepository = monthlyPeriodRepository;
    }

    public MonthlyPeriod getMonthlyPeriod(MonthlyPeriodId id) {
        return monthlyPeriodRepository.getMonthlyPeriod(id);
    }
}
