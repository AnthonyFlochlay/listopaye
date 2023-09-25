package com.listopaye.domain;

import java.time.*;

public class DateTimeFixtures {

    public static int thisYear() {
        return thisYearPlusYears(0);
    }

    public static int thisYearPlusYears(int yearOffset) {
        return ZonedDateTime.now().plusYears(yearOffset).getYear();
    }

    public static LocalDate firstDayOf(int year, Month month) {
        return YearMonth.of(year, month).atDay(1);
    }

    public static LocalDate lastDayOf(int year, Month month) {
        return YearMonth.of(year, month).atEndOfMonth();
    }
}
