package com.listopaye.domain;

import org.junit.jupiter.api.Test;

import static com.listopaye.domain.DateTimeFixtures.aTimeAfter;
import static com.listopaye.domain.DateTimeFixtures.aTimeBefore;
import static org.assertj.core.api.Assertions.assertThat;

interface StartsBeforeOrEqualTimeTest {

    @Test
    default void period_starts_from_its_start_date() {
        Period thePeriod = aPeriod();
        assertThat(
                thePeriod.startsBeforeOrEqual(thePeriod.startDateTime())
        ).isTrue();
    }

    @Test
    default void period_starts_before_any_instant_after_its_start_date() {
        Period thePeriod = aPeriod();
        assertThat(
                thePeriod.startsBeforeOrEqual(aTimeAfter(thePeriod))
        ).isTrue();
    }

    @Test
    default void period_does_not_start_before_any_instant_before_its_start_date() {
        Period thePeriod = aPeriod();
        assertThat(
                thePeriod.startsBeforeOrEqual(aTimeBefore(thePeriod))
        ).isFalse();
    }

    Period aPeriod();

}