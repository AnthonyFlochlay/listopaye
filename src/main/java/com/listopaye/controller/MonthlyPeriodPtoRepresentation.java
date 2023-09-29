package com.listopaye.controller;

import com.listopaye.domain.MonthlyPeriodPto;

import java.time.LocalDate;
import java.util.UUID;

public record MonthlyPeriodPtoRepresentation(UUID id, String employeeName, LocalDate startDate, LocalDate endDate) {

    public static MonthlyPeriodPtoRepresentation of(UUID id, String employeeName, LocalDate startDate, LocalDate endDate) {
        return new MonthlyPeriodPtoRepresentation(id, employeeName, startDate, endDate);
    }

    public static MonthlyPeriodPtoRepresentation of(MonthlyPeriodPto pto) {
        return new MonthlyPeriodPtoRepresentation(
                pto.id(),
                pto.employeeName(),
                pto.startDate(),
                pto.endDate()
        );
    }
}
