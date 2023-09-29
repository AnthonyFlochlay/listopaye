package com.listopaye;

import com.listopaye.controller.MonthlyPeriodIdRepresentation;
import com.listopaye.domain.MonthlyPeriod;

import java.time.Month;

public record MonthlyPeriodRepresentation(String id, int year, Month month) {
    public static MonthlyPeriodRepresentation of(String id, int year, Month month) {
        return new MonthlyPeriodRepresentation(id, year, month);
    }

    public static MonthlyPeriodRepresentation of(int year, Month month) {
        return new MonthlyPeriodRepresentation(MonthlyPeriodIdRepresentation.of(year, month), year, month);
    }

    public static MonthlyPeriodRepresentation of(MonthlyPeriod monthlyPeriod) {
        return MonthlyPeriodRepresentation.of(monthlyPeriod.year(), monthlyPeriod.month());
    }
}
