package com.listopaye.domain;

import java.time.*;
import java.util.Random;

public class DateTimeFixtures {

    public static final Random RANDOM = new Random();

    public static int aYear() {
        return RANDOM.nextInt(thisYearMinusYears(10), thisYearPlusYears(10));
    }

    public static int thisYear() {
        return thisYearPlusYears(0);
    }

    public static int thisYearPlusYears(int yearOffset) {
        return ZonedDateTime.now().plusYears(yearOffset).getYear();
    }

    public static int thisYearMinusYears(int yearOffset) {
        return ZonedDateTime.now().minusYears(yearOffset).getYear();
    }

    public static Month aMonth() {
        Month[] allMonths = Month.values();
        return allMonths[RANDOM.nextInt(allMonths.length)];
    }

    public static LocalDate firstDayOf(int year, Month month) {
        return YearMonth.of(year, month).atDay(1);
    }

    public static LocalDate lastDayOf(int year, Month month) {
        return YearMonth.of(year, month).atEndOfMonth();
    }
}
