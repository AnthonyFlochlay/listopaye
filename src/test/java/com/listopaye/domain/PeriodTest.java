package com.listopaye.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.listopaye.domain.DateTimeFixtures.*;
import static com.listopaye.domain.MonthlyPeriod.DEFAULT_ZONE_ID;
import static com.listopaye.domain.MonthlyPeriodFixtures.monthlyPeriodFollowing;
import static java.time.Month.*;
import static org.assertj.core.api.Assertions.assertThat;

class PeriodTest {

    @Test
    void march_period_of_current_year_starts_the_first_of_march_of_that_year_at_midnight() {
        // Given
        var theMonthlyPeriod = new MonthlyPeriod(thisYear(), MARCH);
        // When/Then
        assertThat(
                theMonthlyPeriod.startDateTime()
        ).isEqualTo(
                ZonedDateTime.of(thisYear(), MARCH.getValue(), 1, 0, 0, 0, 0, DEFAULT_ZONE_ID)
        );
    }

    @Test
    void march_period_of_current_year_ends_the_1st_of_april_of_same_year() {
        // Given
        var theMonthlyPeriod = new MonthlyPeriod(thisYear(), MARCH);
        // When/Then
        assertThat(
                theMonthlyPeriod.endDateTime()
        ).isEqualTo(
                LocalDateTime.of(thisYear(), APRIL, 1, 0, 0, 0, 0).atZone(DEFAULT_ZONE_ID)
        );
    }

    private static Stream<MonthlyPeriod> someMonthlyPeriods() {
        return MonthlyPeriodFixtures.someMonthlyPeriods();
    }

    @MethodSource("someMonthlyPeriods")
    @ParameterizedTest
    void monthly_periods_start_the_first_day_of_the_month_of_the_year_at_midnight(MonthlyPeriod theMonthlyPeriod) {
        ZonedDateTime startDateTime = theMonthlyPeriod.startDateTime();
        assertThat(startDateTime.getYear()).isEqualTo(theMonthlyPeriod.year());
        assertThat(startDateTime.getMonth()).isEqualTo(theMonthlyPeriod.month());
        assertThat(startDateTime.getDayOfMonth()).isEqualTo(1);
        assertThat(startDateTime.toLocalTime()).isEqualTo(LocalTime.of(0, 0, 0, 0));
    }

    @MethodSource("someMonthlyPeriods")
    @ParameterizedTest
    void monthly_periods_end_the_first_instant_of_the_following_period(MonthlyPeriod theMonthlyPeriod) {
        assertThat(
                theMonthlyPeriod.endDateTime()
        ).isEqualTo(
                monthlyPeriodFollowing(theMonthlyPeriod).startDateTime()
        );
    }

    public static Stream<ZonedDateTime> someDateTimes() {
        return IntStream.range(0, 10).mapToObj(
                i -> ZonedDateTime.now()
                        .withYear(aYear())
                        .withMonth(aMonth().getValue())
        );
    }

    @MethodSource("someDateTimes")
    @ParameterizedTest
    void days_are_included_in_their_monthly_period(ZonedDateTime theDateTime) {
        var theMonthlyPeriod = new MonthlyPeriod(theDateTime.getYear(), theDateTime.getMonth());
        assertThat(theMonthlyPeriod.contains(theDateTime)).isTrue();
        assertThat(theMonthlyPeriod.contains(firstInstantOfMonth(theDateTime))).isTrue();
        assertThat(theMonthlyPeriod.contains(firstInstantOfMonth(theDateTime).minusNanos(1))).isFalse();
        assertThat(theMonthlyPeriod.contains(firstInstantOfMonth(theDateTime).plusMonths(1))).isFalse();
        assertThat(theMonthlyPeriod.contains(firstInstantOfMonth(theDateTime).plusMonths(1).minusNanos(1))).isTrue();
    }

}

