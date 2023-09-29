package com.listopaye.domain.spi.stub;

import java.time.Month;

public record MonthlyPeriodId(int year, Month month) {
    public static MonthlyPeriodId of(int year, Month month) {
        return new MonthlyPeriodId(year, month);
    }
}
