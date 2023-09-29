package com.listopaye.domain.spi.stub;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

import static com.listopaye.utils.DateUtils.isAfterOrEqual;
import static com.listopaye.utils.DateUtils.isBeforeOrEqual;
import static java.time.Month.DECEMBER;
import static java.time.Month.JANUARY;

public record MonthlyPeriodId(int year, Month month) {
    public static MonthlyPeriodId of(int year, Month month) {
        return new MonthlyPeriodId(year, month);
    }

    public static MonthlyPeriodId of(LocalDate date) {
        return new MonthlyPeriodId(date.getYear(), date.getMonth());
    }

    public static List<MonthlyPeriodId> allPeriodsBetween(MonthlyPeriodId firstPeriod, MonthlyPeriodId lastPeriod) {
        var periods = new LinkedList<MonthlyPeriodId>();
        periods.add(firstPeriod);

        var current = firstPeriod;
        while (!current.equals(lastPeriod)) {
            current = current.nextPeriod();
            periods.add(current);
        }
        return periods;
    }

    public MonthlyPeriodId nextPeriod() {
        if (lastMonthInYear())
            return MonthlyPeriodId.of(nextYear(), JANUARY);
        return MonthlyPeriodId.of(year, nextMonth());
    }

    private boolean lastMonthInYear() {
        return month == DECEMBER;
    }

    private int nextYear() {
        return year + 1;
    }

    private Month nextMonth() {
        return month.plus(1);
    }

    public boolean contains(LocalDate candidate) {
        return isBeforeOrEqual(startDate(), candidate) && isAfterOrEqual(endDate(), candidate);
    }

    public LocalDate startDate() {
        return LocalDate.of(year, month, 1);
    }

    public LocalDate endDate() {
        return startDate().plusMonths(1).minusDays(1);
    }

    public LocalDate day(int dayInMonth) {
        return LocalDate.of(year, month, dayInMonth);
    }
}
