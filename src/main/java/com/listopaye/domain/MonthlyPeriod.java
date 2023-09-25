package com.listopaye.domain;

import java.time.*;

public record MonthlyPeriod(int year, Month month) {

    public static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("Europe/Paris");

    public ZonedDateTime getStartDateTime() {
        return yearMonth().atEndOfMonth().atStartOfDay().atZone(DEFAULT_ZONE_ID);
    }

    private YearMonth yearMonth() {
        return YearMonth.of(this.year, this.month);
    }

    public ZonedDateTime getEndDateTime() {
        return yearMonth().atEndOfMonth().atTime(LocalTime.MAX).atZone(DEFAULT_ZONE_ID);
    }
}
