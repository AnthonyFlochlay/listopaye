package com.listopaye.controller;

import com.listopaye.domain.MonthlyPeriod;

import java.time.Month;
import java.util.List;

public record MonthlyPeriodRepresentation(String id, int year, Month month, List<MonthlyPeriodPtoRepresentation> ptos) {
    public static MonthlyPeriodRepresentation of(String id, int year, Month month, List<MonthlyPeriodPtoRepresentation> ptos) {
        return new MonthlyPeriodRepresentation(id, year, month, ptos);
    }

    public static MonthlyPeriodRepresentation of(int year, Month month) {
        return new MonthlyPeriodRepresentation(MonthlyPeriodIdRepresentation.of(year, month), year, month, List.of());
    }

    public static MonthlyPeriodRepresentation of(MonthlyPeriod monthlyPeriod) {
        return new MonthlyPeriodRepresentation(
                MonthlyPeriodIdRepresentation.of(monthlyPeriod.year(), monthlyPeriod.month()),
                monthlyPeriod.year(),
                monthlyPeriod.month(),
                MonthlyPeriodPtosRepresentation.of(monthlyPeriod)
        );
    }
}
