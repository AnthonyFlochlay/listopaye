package com.listopaye.domain;

import com.listopaye.DateFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PtoTest {

    @Test
    void should_throw_if_end_date_before_start_date() {
        var startDate = DateFixtures.aDay();
        Assertions.assertThatThrownBy(
                () -> Pto.of(UUIDFixtures.anUuid(), "employee1", startDate, startDate.minusDays(1))
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("End date should be after start date");
    }

    private Pto aPto() {
        var startDate = DateFixtures.aDay();
        return Pto.of(UUIDFixtures.anUuid(), "employee1", startDate, startDate.plusDays(2));
    }
}