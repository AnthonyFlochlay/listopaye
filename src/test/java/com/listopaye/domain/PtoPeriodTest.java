package com.listopaye.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.listopaye.domain.DateTimeFixtures.*;
import static com.listopaye.domain.MonthlyPeriodFixtures.monthlyPeriodContaining;
import static java.time.Month.*;
import static org.assertj.core.api.Assertions.assertThat;

public class PtoPeriodTest implements PeriodTest {

    private static Stream<PtoPeriod> somePtosOnOneMonth() {
        return Stream.of(
                PtoPeriod.ofOneDay(dayOf(thisYear(), APRIL, 25)),
                PtoPeriod.of(dayOf(thisYear(), APRIL, 25), dayOf(thisYear(), APRIL, 28)),
                PtoPeriod.of(dayOf(thisYear(), APRIL, 1), dayOf(thisYear(), APRIL, 30))
        );
    }

    @MethodSource("somePtosOnOneMonth")
    @ParameterizedTest
    void pto_on_one_month_is_fully_included_in_the_monthly_period(PtoPeriod thePtoOnOneMonth) {
        assertThat(
                thePtoOnOneMonth.isFullyIncludedIn(monthlyPeriodContaining(thePtoOnOneMonth.startDateTime()))
        ).isTrue();
    }

    private static Stream<PtoPeriod> somePtosOnSeveralMonths() {
        return Stream.of(
                PtoPeriod.of(dayOf(thisYear(), APRIL, 25), dayOf(thisYear(), MAY, 1)),
                PtoPeriod.of(dayOf(thisYear(), MARCH, 12), dayOf(thisYear(), JUNE, 18))
        );
    }

    @MethodSource("somePtosOnSeveralMonths")
    @ParameterizedTest
    void pto_on_several_months_is_not_fully_included_in_those_months(PtoPeriod ptoOnSeveralMonths) {
        assertThat(ptoOnSeveralMonths.isFullyIncludedIn(monthlyPeriodContaining(ptoOnSeveralMonths.startDateTime()))).isFalse();
        assertThat(ptoOnSeveralMonths.isFullyIncludedIn(monthlyPeriodContaining(ptoOnSeveralMonths.endDateTime()))).isFalse();
    }

    @MethodSource("somePtosOnOneMonth")
    @ParameterizedTest
    void pto_on_one_month_are_partially_included_in_the_monthly_period(PtoPeriod aPtoOnOneMonth) {
        assertThat(
                aPtoOnOneMonth.isPartiallyIncludedIn(monthlyPeriodContaining(aPtoOnOneMonth.startDateTime()))
        ).isTrue();
    }

    @Override
    public PtoPeriod aPeriod() {
        var start = aDateTime();
        return PtoPeriod.of(start, start.plusDays(1));
    }
}
