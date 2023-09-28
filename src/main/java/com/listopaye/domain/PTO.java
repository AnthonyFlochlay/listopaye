package com.listopaye.domain;

import java.time.ZonedDateTime;

public record PTO(ZonedDateTime start, ZonedDateTime end) {
    public static PTO of(ZonedDateTime start, ZonedDateTime end) {
        return new PTO(start, end);
    }
    public static PTO of(ZonedDateTime start) {
        return new PTO(start, start.plusDays(1));
    }

    public boolean isFullyIncludedIn(MonthlyPeriod period) {
        return period.contains(start) && period.contains(end);
    }
}
