package com.listopaye.domain;

import java.time.ZonedDateTime;

/**
 * A period of paid time off
 * <p>
 * For example an employee can declare a PTO for whole April, 25th or for a period from April, 25th to March, 12th
 */
public record PTO(ZonedDateTime startDateTime, ZonedDateTime endDateTime) implements Period {
    public static PTO of(ZonedDateTime start, ZonedDateTime end) {
        return new PTO(start, end);
    }

    public static PTO of(ZonedDateTime start) {
        return new PTO(start, start.plusDays(1));
    }

}
