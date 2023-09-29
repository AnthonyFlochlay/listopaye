package com.listopaye.domain.spi.stub;

import com.listopaye.domain.MonthlyPeriod;
import com.listopaye.domain.MonthlyPeriodPto;
import com.listopaye.domain.spi.MonthlyPeriodRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiFunction;
import java.util.stream.Stream;

@Repository
public class MonthlyPeriodRepositoryStub implements MonthlyPeriodRepository {

    private final ConcurrentMap<MonthlyPeriodId, MonthlyPeriod> monthlyPeriods = new ConcurrentHashMap<>();

    @Override
    public MonthlyPeriod getMonthlyPeriod(MonthlyPeriodId id) {
        var newMonthlyPeriod = MonthlyPeriod.of(id.year(), id.month());
        var existingMonthlyPeriod = monthlyPeriods.putIfAbsent(id, newMonthlyPeriod);
        return existingMonthlyPeriod == null
                ? newMonthlyPeriod
                : existingMonthlyPeriod;
    }

    @Override
    public void addPto(MonthlyPeriodPto monthlyPto) {
        monthlyPeriods.merge(monthlyPto.monthlyPeriodId(), monthlyPeriodOf(monthlyPto), MonthlyPeriod::merge);
    }

    private MonthlyPeriod monthlyPeriodOf(MonthlyPeriodPto monthlyPto) {
        return MonthlyPeriod.of(monthlyPto);
    }

}
