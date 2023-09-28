package com.listopaye.domain;

import java.time.*;
import java.util.Random;

public class DateTimeFixtures {

    public static final Random RANDOM = new Random();

    public static int aYear() {
        return RANDOM.nextInt(thisYearMinusYears(10), thisYearPlusYears(10));
    }

    public static int thisYear() {
        return thisYearPlusYears(0);
    }

    public static int thisYearPlusYears(int yearOffset) {
        return ZonedDateTime.now().plusYears(yearOffset).getYear();
    }

    public static int thisYearMinusYears(int yearOffset) {
        return ZonedDateTime.now().minusYears(yearOffset).getYear();
    }

    public static Month aMonth() {
        Month[] allMonths = Month.values();
        return allMonths[RANDOM.nextInt(allMonths.length)];
    }

    public static ZonedDateTime firstInstantOfMonth(ZonedDateTime theDateTime) {
        return theDateTime.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

}
