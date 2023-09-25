package com.listopaye.domain;

import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public record MonthlyPeriod(int year, Month month) {

    public static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("Europe/Paris");

    public ZonedDateTime getStartDate() {
        return ZonedDateTime.of(this.year, this.month.getValue(), 1, 0, 0, 0, 0, DEFAULT_ZONE_ID);
    }
}
