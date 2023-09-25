package com.listopaye.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalTime;
import java.time.Month;
import java.time.ZonedDateTime;
import java.util.stream.Stream;

import static com.listopaye.domain.DateTimeFixtures.*;
import static com.listopaye.domain.MonthlyPeriod.DEFAULT_ZONE_ID;
import static java.time.Month.MARCH;
import static org.assertj.core.api.Assertions.assertThat;

class PeriodTest {

    @Test
    void march_period_of_current_year_starts_the_first_of_march_of_that_year_at_midnight() {
        // Given
        var theMonthlyPeriod = new MonthlyPeriod(thisYear(), MARCH);
        // When/Then
        assertThat(
                theMonthlyPeriod.getStartDateTime()
        ).isEqualTo(
                ZonedDateTime.of(thisYear(), MARCH.getValue(), 1, 0, 0, 0, 0, DEFAULT_ZONE_ID)
        );
    }

    @Test
    void march_period_of_current_year_ends_the_31_of_march_of_that_year_at_next_midnight() {
        // Given
        var theMonthlyPeriod = new MonthlyPeriod(thisYear(), MARCH);
        // When/Then
        assertThat(
                theMonthlyPeriod.getEndDateTime()
        ).isEqualTo(
                ZonedDateTime.of(thisYear(), MARCH.getValue(), 31, 23, 59, 59, 999999999, DEFAULT_ZONE_ID)
        );
    }

    @EnumSource(Month.class)
    @ParameterizedTest
    void monthly_period_should_start_the_first_of_month_at_midnight(Month month) {
        // Given
        int theYear = aYear();
        var monthlyPeriod = new MonthlyPeriod(theYear, month);
        // When/Then
        assertThat(
                monthlyPeriod.getStartDateTime()
        ).isEqualTo(
                firstDayOf(2023, month).atStartOfDay().atZone(DEFAULT_ZONE_ID)
        );
    }

    private static int aYear() {
        return thisYear();
    }

    @MethodSource("someYears")
    @ParameterizedTest
    void monthly_period_should_start_the_first_of_month_at_midnight_of_the_year(int theYear) {
        // Given
        Month theMonth = aMonth();
        var monthlyPeriod = new MonthlyPeriod(theYear, theMonth);
        // When/Then
        assertThat(
                monthlyPeriod.getStartDateTime()
        ).isEqualTo(
                firstDayOf(theYear, theMonth).atStartOfDay().atZone(DEFAULT_ZONE_ID)
        );
    }

    public static Stream<Integer> someYears() {
        return Stream.of(
                2022, 2023, 2024, thisYear(), thisYearPlusYears(1), thisYearPlusYears(10), thisYearPlusYears(-7)
        );
    }

    private static Month aMonth() {
        return Month.JULY;
    }

}