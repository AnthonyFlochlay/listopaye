package com.listopaye.controller;

import com.listopaye.domain.MonthlyPeriod;

import java.util.List;

public class MonthlyPeriodPtosRepresentation {

    public static List<MonthlyPeriodPtoRepresentation> of(MonthlyPeriod monthlyPeriod) {
        return monthlyPeriod.ptos().stream()
                .map(MonthlyPeriodPtoRepresentation::of)
                .toList();
    }
}
