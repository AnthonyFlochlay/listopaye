package com.listopaye.domain;

import java.time.ZonedDateTime;

/**
 * A period of paid time off
 * <p>
 * For example an employee can declare a PTO for whole April, 25th or for a period from April, 25th to March, 12th
 */
public record PtoPeriod(ZonedDateTime startDateTime, ZonedDateTime endDateTime) implements Period {
    public static PtoPeriod of(ZonedDateTime start, ZonedDateTime end) {
        return new PtoPeriod(start, end);
    }

    public static PtoPeriod ofOneDay(ZonedDateTime start) {
        return new PtoPeriod(start, start.plusDays(1));
    }

}
