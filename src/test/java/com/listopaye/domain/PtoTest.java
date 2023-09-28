package com.listopaye.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.listopaye.domain.DateTimeFixtures.dayOf;
import static com.listopaye.domain.DateTimeFixtures.thisYear;
import static com.listopaye.domain.MonthlyPeriodFixtures.monthlyPeriodContaining;
import static java.time.Month.*;
import static org.assertj.core.api.Assertions.assertThat;

public class PtoTest {

    private static Stream<PTO> somePtosOnOneMonth() {
        return Stream.of(
                PTO.of(dayOf(thisYear(), APRIL, 25)),
                PTO.of(dayOf(thisYear(), APRIL, 25), dayOf(thisYear(), APRIL, 28)),
                PTO.of(dayOf(thisYear(), APRIL, 1), dayOf(thisYear(), APRIL, 30))
        );
    }

    @MethodSource("somePtosOnOneMonth")
    @ParameterizedTest
    void pto_on_one_month_is_fully_included_in_the_monthly_period(PTO thePtoOnOneMonth) {
        assertThat(
                thePtoOnOneMonth.isFullyIncludedIn(monthlyPeriodContaining(thePtoOnOneMonth.startDateTime()))
        ).isTrue();
    }

    private static Stream<PTO> somePtosOnSeveralMonths() {
        return Stream.of(
                PTO.of(dayOf(thisYear(), APRIL, 25), dayOf(thisYear(), MAY, 1)),
                PTO.of(dayOf(thisYear(), MARCH, 12), dayOf(thisYear(), JUNE, 18))
        );
    }

    @MethodSource("somePtosOnSeveralMonths")
    @ParameterizedTest
    void pto_on_several_months_is_not_fully_included_in_those_months(PTO ptoOnSeveralMonths) {
        assertThat(ptoOnSeveralMonths.isFullyIncludedIn(monthlyPeriodContaining(ptoOnSeveralMonths.startDateTime()))).isFalse();
        assertThat(ptoOnSeveralMonths.isFullyIncludedIn(monthlyPeriodContaining(ptoOnSeveralMonths.endDateTime()))).isFalse();
    }

    @MethodSource("somePtosOnOneMonth")
    @ParameterizedTest
    void pto_on_one_month_are_partially_included_in_the_monthly_period(PTO aPtoOnOneMonth) {
        assertThat(
                aPtoOnOneMonth.isPartiallyIncludedIn(monthlyPeriodContaining(aPtoOnOneMonth.startDateTime()))
        ).isTrue();
    }

}
