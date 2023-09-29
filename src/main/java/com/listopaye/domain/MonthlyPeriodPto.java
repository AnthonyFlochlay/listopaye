package com.listopaye.domain;

import com.listopaye.domain.spi.stub.MonthlyPeriodId;

import java.time.LocalDate;
import java.util.UUID;

public record MonthlyPeriodPto(UUID id, String employeeName, LocalDate startDate, LocalDate endDate) {
    public static MonthlyPeriodPto of(UUID id, String employeeName, LocalDate startDate, LocalDate endDate) {
        return new MonthlyPeriodPto(id, employeeName, startDate, endDate);
    }

    public MonthlyPeriodId monthlyPeriodId() {
        return MonthlyPeriodId.of(startDate);
    }
}
