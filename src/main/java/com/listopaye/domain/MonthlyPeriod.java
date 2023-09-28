package com.listopaye.domain;

import java.time.*;

/**
 * The monthly period
 * <p>
 * For example, the "April 2023" monthly period starts the 1st of April 2023 and ends the 30 of April 2023
 */
public record MonthlyPeriod(int year, Month month) {

    public static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("Europe/Paris");
    private static final int FIRST_DAY_OF_MONTH = 1;

    public ZonedDateTime getStartDateTime() {
        return LocalDateTime.of(this.year, this.month, FIRST_DAY_OF_MONTH, 0, 0, 0, 0).atZone(DEFAULT_ZONE_ID);
    }

    public ZonedDateTime getEndDateTime() {
        return yearMonth().atEndOfMonth().atTime(LocalTime.MAX).atZone(DEFAULT_ZONE_ID);
    }

    private YearMonth yearMonth() {
        return YearMonth.of(this.year, this.month);
    }

    public boolean contains(ZonedDateTime dateTime) {
        return isAfterOrEqualToStartDateTime(dateTime) && isBeforeOrEqualToEndDateTime(dateTime);
    }

    private boolean isAfterOrEqualToStartDateTime(ZonedDateTime dateTime) {
        var startDateTime = getStartDateTime();
        return dateTime.isAfter(startDateTime) || dateTime.equals(startDateTime);
    }

    private boolean isBeforeOrEqualToEndDateTime(ZonedDateTime dateTime) {
        var endDateTime = getEndDateTime();
        return dateTime.isBefore(endDateTime) || dateTime.equals(endDateTime);
    }
}
