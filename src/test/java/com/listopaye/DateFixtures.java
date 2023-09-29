package com.listopaye;

import java.time.ZonedDateTime;

public class DateFixtures {
    public static int thisYear() {
        return ZonedDateTime.now().getYear();
    }
}
