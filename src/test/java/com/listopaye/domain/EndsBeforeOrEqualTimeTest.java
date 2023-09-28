package com.listopaye.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

interface EndsBeforeOrEqualTimeTest {

    @Test
    default void period_ends_at_its_end_date() {
        Period thePeriod = aPeriod();
        assertThat(
                thePeriod.endsAfterOrEqual(thePeriod.endDateTime())
        ).isTrue();
    }

    @Test
    default void period_ends_after_any_instant_before_its_end_date() {
        Period thePeriod = aPeriod();
        assertThat(
                thePeriod.endsAfterOrEqual(thePeriod.endDateTime().minusMinutes(1))
        ).isTrue();
    }

    Period aPeriod();

}