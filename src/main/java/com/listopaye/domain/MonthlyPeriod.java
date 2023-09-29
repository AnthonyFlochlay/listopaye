package com.listopaye.domain;

import com.listopaye.domain.spi.stub.MonthlyPeriodId;

import java.time.Month;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

//TODO: use MonthlyPeriodId instead of year/month
public record MonthlyPeriod(int year, Month month, List<MonthlyPeriodPto> ptos) {
    public static MonthlyPeriod of(int year, Month month) {
        return new MonthlyPeriod(year, month, List.of());
    }

    public static MonthlyPeriod of(int year, Month month, List<MonthlyPeriodPto> ptos) {
        return new MonthlyPeriod(year, month, ptos);
    }

    public static MonthlyPeriod of(MonthlyPeriodPto monthlyPto) {
        return new MonthlyPeriod(monthlyPto.monthlyPeriodId().year(), monthlyPto.monthlyPeriodId().month(), List.of(monthlyPto));
    }

    public MonthlyPeriod merge(MonthlyPeriod other) {
        if (!this.monthlyPeriodId().equals(other.monthlyPeriodId()))
            throw new IllegalArgumentException("Cannot merge periods on different months!");
        return MonthlyPeriod.of(
                year,
                month,
                Stream.of(this.ptos(), other.ptos()).flatMap(Collection::stream)
//                        TODO: use distinct()
                        .toList()
        );
    }

    private void checkSamePeriodIds(MonthlyPeriod other) {
        if (!this.monthlyPeriodId().equals(other.monthlyPeriodId()))
            throw new IllegalArgumentException("Cannot merge periods on different month: ");
    }

    private MonthlyPeriodId monthlyPeriodId() {
        return MonthlyPeriodId.of(year, month);
    }

    public MonthlyPeriod withMonth(Month newMonth) {
        return new MonthlyPeriod(
                this.year,
                newMonth,
                this.ptos
        );
    }
}
