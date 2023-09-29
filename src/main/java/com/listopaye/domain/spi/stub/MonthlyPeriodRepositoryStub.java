package com.listopaye.domain.spi.stub;

import com.listopaye.domain.MonthlyPeriod;
import com.listopaye.domain.spi.MonthlyPeriodRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MonthlyPeriodRepositoryStub implements MonthlyPeriodRepository {

    private final Map<MonthlyPeriodId, MonthlyPeriod> monthlyPeriods = new HashMap<>();

    @Override
    public MonthlyPeriod getMonthlyPeriod(MonthlyPeriodId id) {
        if (!monthlyPeriods.containsKey(id)) {
            createMonthlyPeriod(id);
        }
        return monthlyPeriods.get(id);
    }

    private void createMonthlyPeriod(MonthlyPeriodId id) {
        monthlyPeriods.put(id, MonthlyPeriod.of(id.year(), id.month()));
    }
}
