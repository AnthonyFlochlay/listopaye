package com.listopaye.domain;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static com.listopaye.domain.DateTimeFixtures.thisYear;
import static java.time.Month.APRIL;
import static org.assertj.core.api.Assertions.assertThat;

public class PtoTest {

    @Test
    void pto_on_25_april_is_fully_included_in_april_period() {
        var thePto = PTO.of(ZonedDateTime.now().withMonth(APRIL.getValue()).withDayOfMonth(25));
        var aprilPeriod = MonthlyPeriod.of(thisYear(), APRIL);

        assertThat(
            thePto.isFullyIncludedIn(aprilPeriod)
        ).isTrue();
    }
}
