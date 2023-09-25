package com.listopaye.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZonedDateTime;
import java.util.stream.Stream;

import static com.listopaye.domain.MonthlyPeriod.DEFAULT_ZONE_ID;
import static java.time.Month.MARCH;
import static org.assertj.core.api.Assertions.assertThat;

class PeriodTest {

    @Test
    void march_period_should_start_the_first_of_march_at_midnight() {
        // Given
        var marchPeriod = new MonthlyPeriod(2023, MARCH);
        // When/Then
        assertThat(
                marchPeriod.getStartDate()
        ).isEqualTo(
                firstDayOf(2023, MARCH).atStartOfDay().atZone(DEFAULT_ZONE_ID)
        );
    }

    private LocalDate firstDayOf(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    @EnumSource(Month.class)
    @ParameterizedTest
    void monthly_period_should_start_the_first_of_month_at_midnight(Month month) {
        // Given
        int theYear = aYear();
        var monthlyPeriod = new MonthlyPeriod(theYear, month);
        // When/Then
        assertThat(
                monthlyPeriod.getStartDate()
        ).isEqualTo(
                firstDayOf(2023, month).atStartOfDay().atZone(DEFAULT_ZONE_ID)
        );
    }

    private static int aYear() {
        return 2023;
    }

    @MethodSource("someYears")
    @ParameterizedTest
    void monthly_period_should_start_the_first_of_month_at_midnight_of_the_year(int theYear) {
        // Given
        Month theMonth = aMonth();
        var monthlyPeriod = new MonthlyPeriod(theYear, theMonth);
        // When/Then
        assertThat(
                monthlyPeriod.getStartDate()
        ).isEqualTo(
                firstDayOf(theYear, theMonth).atStartOfDay().atZone(DEFAULT_ZONE_ID)
        );
    }

    public static Stream<Integer> someYears() {
        return Stream.of(
                2022, 2023, 2024, thisYear(), thisYearPlusYears(1), thisYearPlusYears(10), thisYearPlusYears(-7)
        );
    }

    private static Integer thisYear() {
        return thisYearPlusYears(0);
    }

    private static int thisYearPlusYears(int yearOffset) {
        return ZonedDateTime.now().plusYears(yearOffset).getYear();
    }

    private static Month aMonth() {
        return Month.JULY;
    }

}