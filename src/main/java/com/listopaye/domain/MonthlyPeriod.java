package com.listopaye.domain;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * The monthly period
 * <p>
 * For example, the "April 2023" monthly period starts the 1st of April 2023 and ends the 30 of April 2023
 */
public record MonthlyPeriod(int year, Month month) implements Period {

    public static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("Europe/Paris");
    private static final int FIRST_DAY_OF_MONTH = 1;

    public static MonthlyPeriod of(int year, Month month) {
        return new MonthlyPeriod(year, month);
    }

    @Override
    public ZonedDateTime startDateTime() {
        return LocalDateTime.of(this.year, this.month, FIRST_DAY_OF_MONTH, 0, 0, 0, 0).atZone(DEFAULT_ZONE_ID);
    }

    @Override
    public ZonedDateTime endDateTime() {
        return startDateTime().plusMonths(1);
    }

}
