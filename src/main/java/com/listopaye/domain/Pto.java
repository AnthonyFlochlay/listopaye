package com.listopaye.domain;

import com.listopaye.domain.spi.stub.MonthlyPeriodId;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public record Pto(UUID id, String employeeName, LocalDate startDate, LocalDate endDate) {

    public Pto(UUID id, String employeeName, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.employeeName = employeeName;
        this.startDate = startDate;
        if (endDate.isBefore(startDate))
            throw new IllegalArgumentException("End date should be after start date");
        this.endDate = endDate;
    }

    public static Pto of(UUID id, String employeeName, LocalDate startDate, LocalDate endDate) {
        return new Pto(id, employeeName, startDate, endDate);
    }

    public List<MonthlyPeriodPto> monthlyPeriodPtos() {
        return MonthlyPeriodId.allPeriodsBetween(firstMonthlyPeriod(), lastMonthlyPeriod()).stream()
                .map(period -> MonthlyPeriodPto.of(id, employeeName, startDateInPeriod(period), endDateInPeriod(period)))
                .toList();
    }

    private MonthlyPeriodId lastMonthlyPeriod() {
        return MonthlyPeriodId.of(endDate.getYear(), endDate.getMonth());
    }

    private MonthlyPeriodId firstMonthlyPeriod() {
        return MonthlyPeriodId.of(startDate.getYear(), startDate.getMonth());
    }

    private LocalDate startDateInPeriod(MonthlyPeriodId period) {
        if (period.contains(startDate))
            return startDate;
        return period.startDate();
    }

    private LocalDate endDateInPeriod(MonthlyPeriodId period) {
        if (period.contains(endDate))
            return endDate;
        return period.endDate();
    }
}
