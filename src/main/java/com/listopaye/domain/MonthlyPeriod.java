package com.listopaye.domain;

import java.time.Month;

public record MonthlyPeriod(int year, Month month) {
    public static MonthlyPeriod of(int year, Month month) {
        return new MonthlyPeriod(year, month);
    }

}
