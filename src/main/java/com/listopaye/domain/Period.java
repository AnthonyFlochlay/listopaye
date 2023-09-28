package com.listopaye.domain;

import java.time.ZonedDateTime;

public interface Period {

    ZonedDateTime startDateTime();
    ZonedDateTime endDateTime();
    default boolean contains(ZonedDateTime dateTime) {
        return startsBeforeOrEqual(dateTime) && endsAfter(dateTime);
    }

    default boolean startsBeforeOrEqual(ZonedDateTime dateTime) {
        return startsBefore(dateTime) || startDateTime().isEqual(dateTime);
    }

    private boolean startsBefore(ZonedDateTime dateTime) {
        return startDateTime().isBefore(dateTime);
    }

    default boolean startsBeforeOrEqual(Period period) {
        return startsBeforeOrEqual(period.startDateTime());
    }

    default boolean endsAfterOrEqual(ZonedDateTime dateTime) {
        return endsAfter(dateTime) || endDateTime().isEqual(dateTime);
    }

    private boolean endsAfter(ZonedDateTime dateTime) {
        return endDateTime().isAfter(dateTime);
    }

    default boolean endsAfterOrEqual(Period period) {
        return endsAfterOrEqual(period.endDateTime());
    }

    default boolean isFullyIncludedIn(Period other) {
        return other.startsBeforeOrEqual(this) && other.endsAfterOrEqual(this);
    }

    default boolean isPartiallyIncludedIn(Period other) {
        return startsBeforeOrEqual(other) && other.endsAfterOrEqual(this)
                || isFullyIncludedIn(other)
                || other.startsBeforeOrEqual(this) && endsAfterOrEqual(other);
    }
}
