package com.listopaye.domain;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

import static com.listopaye.domain.MonthlyPeriodFixtures.monthlyPeriodContaining;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public interface StartsBeforeOrEqualPeriodTest {
    @TestFactory
    default Collection<DynamicTest> period_with_start_date_before_or_equal_other_period_starts_before_or_equal_that_period() {
        Period thePeriodStartingBefore = aPeriod();
        return somePeriodsStartingAfterOrEqual(thePeriodStartingBefore.startDateTime()).stream()
                .map(p -> dynamicTest(
                        "%s should starts before or equal %s".formatted(thePeriodStartingBefore, p),
                        () -> assertThat(thePeriodStartingBefore.startsBeforeOrEqual(p)).isTrue())
                ).toList();
    }

    default List<Period> somePeriodsStartingAfterOrEqual(ZonedDateTime dateTime) {
        return List.of(
                monthlyPeriodContaining(dateTime.plusMonths(1)),
                PtoPeriod.ofOneDay(dateTime),
                PtoPeriod.ofOneDay(dateTime.plusDays(1)),
                PtoPeriod.ofOneDay(dateTime.plusMonths(8)),
                PtoPeriod.of(dateTime, dateTime.plusDays(2)),
                PtoPeriod.of(dateTime.plusDays(1), dateTime.plusDays(5))
        );
    }

    Period aPeriod();
}
