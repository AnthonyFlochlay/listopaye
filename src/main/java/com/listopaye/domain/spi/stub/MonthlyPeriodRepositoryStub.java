package com.listopaye.domain.spi.stub;

import com.listopaye.domain.MonthlyPeriod;
import com.listopaye.domain.spi.MonthlyPeriodRepository;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class MonthlyPeriodRepositoryStub implements MonthlyPeriodRepository {

    private final ConcurrentMap<MonthlyPeriodId, MonthlyPeriod> monthlyPeriods = new ConcurrentHashMap<>();

    @Override
    public MonthlyPeriod getMonthlyPeriod(MonthlyPeriodId id) {
        MonthlyPeriod newMonthlyPeriod = MonthlyPeriod.of(id.year(), id.month());
        return monthlyPeriods.putIfAbsent(id, newMonthlyPeriod);
    }

}
