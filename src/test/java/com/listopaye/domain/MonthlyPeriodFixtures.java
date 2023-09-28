package com.listopaye.domain;

import java.time.ZonedDateTime;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.listopaye.domain.DateTimeFixtures.aMonth;
import static com.listopaye.domain.DateTimeFixtures.aYear;

public class MonthlyPeriodFixtures {

    public static MonthlyPeriod aMonthlyPeriod() {
        return new MonthlyPeriod(aYear(), aMonth());
    }

    public static Stream<MonthlyPeriod> someMonthlyPeriods() {
        return IntStream.range(0, 10).mapToObj(
                i -> aMonthlyPeriod()
        );
    }

    public static MonthlyPeriod monthlyPeriodFollowing(MonthlyPeriod monthlyPeriod) {
        var dateTimeInPeriod = aDateTimeInPeriod(monthlyPeriod);
        return monthlyPeriodContaining(
                dateTimeInPeriod.plusMonths(1)
        );
    }

    private static ZonedDateTime aDateTimeInPeriod(MonthlyPeriod monthlyPeriod) {
        return ZonedDateTime.now()
                .withYear(monthlyPeriod.year())
                .withMonth(monthlyPeriod.month().getValue());
    }

    private static MonthlyPeriod monthlyPeriodContaining(ZonedDateTime dateTime) {
        return MonthlyPeriod.of(dateTime.getYear(), dateTime.getMonth());
    }
}
