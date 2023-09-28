package com.listopaye.domain;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

import static com.listopaye.domain.MonthlyPeriodFixtures.monthlyPeriodContaining;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public interface EndsAfterOrEqualTest {

    @TestFactory
    default Collection<DynamicTest> period_with_end_date_after_or_equal_other_period_ends_after_or_equal_that_period() {
        Period thePeriodEndingAfter = aPeriod();
        return somePeriodsEndingBeforeOrEqual(thePeriodEndingAfter.startDateTime()).stream()
                .map(periodEndingBefore -> dynamicTest(
                        "%s should ends after or equal %s".formatted(thePeriodEndingAfter, periodEndingBefore),
                        () -> assertThat(thePeriodEndingAfter.endsAfterOrEqual(periodEndingBefore)).isTrue())
                ).toList();
    }

    default List<Period> somePeriodsEndingBeforeOrEqual(ZonedDateTime dateTime) {
        return List.of(
                monthlyPeriodContaining(dateTime.minusMonths(1)),
                PtoPeriod.ofOneDay(dateTime),
                PtoPeriod.ofOneDay(dateTime.minusDays(1)),
                PtoPeriod.ofOneDay(dateTime.minusMonths(8)),
                PtoPeriod.of(dateTime.minusDays(2), dateTime),
                PtoPeriod.of(dateTime.minusDays(5), dateTime.minusDays(1))
        );
    }

    Period aPeriod();

}
