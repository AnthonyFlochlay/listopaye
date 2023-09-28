package com.listopaye.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.listopaye.domain.DateTimeFixtures.*;
import static com.listopaye.domain.MonthlyPeriod.DEFAULT_ZONE_ID;
import static java.time.Month.*;
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

    private static Stream<MonthlyPeriod> someMonthlyPeriods() {
        return IntStream.range(0, 10).mapToObj(
                i -> aMonthlyPeriod()
        );
    }

    private static MonthlyPeriod aMonthlyPeriod() {
        return new MonthlyPeriod(aYear(), aMonth());
    }

    @MethodSource("someMonthlyPeriods")
    @ParameterizedTest
    void monthly_periods_start_the_first_day_of_the_month_of_the_year_at_midnight(MonthlyPeriod theMonthlyPeriod) {
        ZonedDateTime startDateTime = theMonthlyPeriod.getStartDateTime();
        assertThat(startDateTime.getYear()).isEqualTo(theMonthlyPeriod.year());
        assertThat(startDateTime.getMonth()).isEqualTo(theMonthlyPeriod.month());
        assertThat(startDateTime.getDayOfMonth()).isEqualTo(1);
        assertThat(startDateTime.toLocalTime()).isEqualTo(LocalTime.of(0, 0, 0, 0));
    }

    private static Stream<Arguments> someMonthlyPeriodsWithLastDay() {
        return Stream.of(
                Arguments.of(new MonthlyPeriod(aYear(), JANUARY), 31),
                Arguments.of(new MonthlyPeriod(2023, FEBRUARY), 28),
                Arguments.of(new MonthlyPeriod(aYear(), MARCH), 31),
                Arguments.of(new MonthlyPeriod(aYear(), APRIL), 30),
                Arguments.of(new MonthlyPeriod(aYear(), MAY), 31),
                Arguments.of(new MonthlyPeriod(aYear(), JUNE), 30),
                Arguments.of(new MonthlyPeriod(aYear(), JULY), 31),
                Arguments.of(new MonthlyPeriod(aYear(), AUGUST), 31),
                Arguments.of(new MonthlyPeriod(aYear(), SEPTEMBER), 30),
                Arguments.of(new MonthlyPeriod(aYear(), OCTOBER), 31),
                Arguments.of(new MonthlyPeriod(aYear(), NOVEMBER), 30),
                Arguments.of(new MonthlyPeriod(aYear(), DECEMBER), 31)
        );
    }

    @MethodSource("someMonthlyPeriodsWithLastDay")
    @ParameterizedTest
    void monthly_periods_end_the_last_day_of_the_month_of_the_year_at_next_midnight(MonthlyPeriod theMonthlyPeriod, int expectedLastDay) {
        var theEndDateTime = theMonthlyPeriod.getEndDateTime();

        assertThat(theEndDateTime.getYear()).isEqualTo(theMonthlyPeriod.year());
        assertThat(theEndDateTime.getMonth()).isEqualTo(theMonthlyPeriod.month());
        assertThat(theEndDateTime.getDayOfMonth()).isEqualTo(expectedLastDay);
        assertThat(theEndDateTime.toLocalTime()).isEqualTo(LocalTime.of(23, 59, 59, 999999999));
    }

    private static Stream<Arguments> someFebruaryPeriodsWithLastDay() {
        return Stream.of(
                Arguments.of(new MonthlyPeriod(2020, FEBRUARY), 29),
                Arguments.of(new MonthlyPeriod(2021, FEBRUARY), 28),
                Arguments.of(new MonthlyPeriod(2022, FEBRUARY), 28),
                Arguments.of(new MonthlyPeriod(2023, FEBRUARY), 28),
                Arguments.of(new MonthlyPeriod(2024, FEBRUARY), 29),
                Arguments.of(new MonthlyPeriod(2030, FEBRUARY), 28),
                Arguments.of(new MonthlyPeriod(2100, FEBRUARY), 28)
        );
    }

    @MethodSource("someFebruaryPeriodsWithLastDay")
    @ParameterizedTest
    void february_periods_end_at_the_last_day_the_month(MonthlyPeriod theMonthlyPeriod, int expectedLastDay) {
        assertThat(theMonthlyPeriod.getEndDateTime().getDayOfMonth()).isEqualTo(expectedLastDay);
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

    private static ZonedDateTime firstInstantOfMonth(ZonedDateTime theDateTime) {
        return theDateTime.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
    }
}

