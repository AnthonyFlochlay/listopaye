package com.listopaye;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZonedDateTime;

public class DateFixtures {
    public static int thisYear() {
        return ZonedDateTime.now().getYear();
    }

    public static Month thisMonth() {
        return ZonedDateTime.now().getMonth();
    }

    public static LocalDate aDay() {
        return LocalDate.now();
    }
}
