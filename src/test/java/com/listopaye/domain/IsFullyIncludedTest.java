package com.listopaye.domain;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.ArrayList;
import java.util.Collection;

import static com.listopaye.domain.MonthlyPeriodFixtures.monthlyPeriodContaining;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public interface IsFullyIncludedTest {

    @TestFactory
    default Collection<DynamicTest> period_with_both_start_date_and_end_date_in_other_period_is_fully_included_in_that_period() {
        Period theFullyIncludedPeriod = aPeriod();
        return somePeriodsFullyIncluding(theFullyIncludedPeriod).stream()
                .map(includingPeriod -> dynamicTest(
                        "%s should be included in %s".formatted(theFullyIncludedPeriod, includingPeriod),
                        () -> assertThat(theFullyIncludedPeriod.isFullyIncludedIn(includingPeriod)).isTrue())
                ).toList();
    }

    default Collection<Period> somePeriodsFullyIncluding(Period includedPeriod) {
        var includingPeriods = new ArrayList<Period>();

        // Add including MonthlyPeriod if possible
        if (includedPeriod instanceof MonthlyPeriod includedMonth) {
            includingPeriods.add(MonthlyPeriod.of(includedMonth.year(), includedMonth.month()));
        } else if (includedPeriod instanceof PtoPeriod includedPto) {
            if (includedPto.isSingleMonth())
                includingPeriods.add(monthlyPeriodContaining(includedPto.startDateTime()));
            // else monthly period cannot include several months PTO
        }
        // Add PTO including periods
        includingPeriods.add(PtoPeriod.of(includedPeriod.startDateTime(), includedPeriod.endDateTime()));
        includingPeriods.add(PtoPeriod.of(includedPeriod.startDateTime().minusDays(2), includedPeriod.endDateTime()));
        includingPeriods.add(PtoPeriod.of(includedPeriod.startDateTime(), includedPeriod.endDateTime().plusDays(3)));
        includingPeriods.add(PtoPeriod.of(includedPeriod.startDateTime().minusMonths(2), includedPeriod.endDateTime().plusMonths(3)));
        return includingPeriods;
    }

    Period aPeriod();

}
