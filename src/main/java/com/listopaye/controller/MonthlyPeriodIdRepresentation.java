package com.listopaye.controller;

import com.listopaye.domain.spi.stub.MonthlyPeriodId;

import java.time.Month;

public class MonthlyPeriodIdRepresentation {

    public static String of(int year, Month month) {
        return year + "-" + month.getValue();
    }

    public static MonthlyPeriodId monthlyPeriodIdOf(String id) {
        var split = id.split("-");
        return MonthlyPeriodId.of(yearOf(split[0]), monthOf(split[1]));
    }

    private static int yearOf(String year) {
        return Integer.parseInt(year);
    }

    private static Month monthOf(String monthIndex) {
        return Month.of(Integer.parseInt(monthIndex));
    }
}
