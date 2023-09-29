package com.listopaye.domain;

import com.listopaye.domain.spi.stub.MonthlyPeriodId;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static com.listopaye.DateFixtures.thisMonth;
import static com.listopaye.DateFixtures.thisYear;
import static com.listopaye.domain.UUIDFixtures.uuidOf;
import static org.assertj.core.api.Assertions.assertThat;

class MonthlyPeriodTest {

    @Test
    void merge_2_periods() {
        // Given
        var theMonthlyPeriod = thisMonthlyPeriod();
        var period1 = MonthlyPeriod.of(
                theMonthlyPeriod.year(),
                theMonthlyPeriod.month(), List.of(
                        MonthlyPeriodPto.of(uuidOf("1"), "employee1", theMonthlyPeriod.day(2), theMonthlyPeriod.day(3)))
        );
        var period2 = MonthlyPeriod.of(
                theMonthlyPeriod.year(),
                theMonthlyPeriod.month(), List.of(
                        MonthlyPeriodPto.of(uuidOf("2"), "employee2", theMonthlyPeriod.day(10), theMonthlyPeriod.day(12)))
        );
        // When/Then
        assertThat(period1.merge(period2)).isEqualTo(
                MonthlyPeriod.of(
                        theMonthlyPeriod.year(),
                        theMonthlyPeriod.month(), List.of(
                                MonthlyPeriodPto.of(uuidOf("1"), "employee1", theMonthlyPeriod.day(2), theMonthlyPeriod.day(3)),
                                MonthlyPeriodPto.of(uuidOf("2"), "employee2", theMonthlyPeriod.day(10), theMonthlyPeriod.day(12)))
                )
        );
    }

    private static UUID anId() {
        return UUID.randomUUID();
    }

    private MonthlyPeriodId thisMonthlyPeriod() {
        return MonthlyPeriodId.of(thisYear(), thisMonth());
    }
}