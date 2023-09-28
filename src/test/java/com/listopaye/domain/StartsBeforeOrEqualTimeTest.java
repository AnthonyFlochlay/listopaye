package com.listopaye.domain;

import org.junit.jupiter.api.Test;

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
                thePeriod.startsBeforeOrEqual(thePeriod.startDateTime().plusMinutes(1))
        ).isTrue();
    }

    Period aPeriod();

}