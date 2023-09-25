package com.listopaye.domain;

import java.time.*;

public record MonthlyPeriod(int year, Month month) {

    public static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("Europe/Paris");
    private static final int FIRST_DAY_OF_MONTH = 1;

    public ZonedDateTime getStartDateTime() {
        return LocalDateTime.of(this.year, this.month, FIRST_DAY_OF_MONTH, 0, 0, 0, 0)
                .atZone(DEFAULT_ZONE_ID);
    }

    public ZonedDateTime getEndDateTime() {
        return yearMonth().atEndOfMonth().atTime(LocalTime.MAX).atZone(DEFAULT_ZONE_ID);
    }

    private YearMonth yearMonth() {
        return YearMonth.of(this.year, this.month);
    }

    public boolean contains(ZonedDateTime dateTime) {
        return getStartDateTime().isBefore(dateTime)
                && getEndDateTime().isAfter(dateTime);
    }
}
