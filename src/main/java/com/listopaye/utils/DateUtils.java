package com.listopaye.utils;

import java.time.LocalDate;

public class DateUtils {
    public static boolean isBeforeOrEqual(LocalDate reference, LocalDate candidate) {
        return reference.isBefore(candidate) || reference.isEqual(candidate);
    }

    public static boolean isAfterOrEqual(LocalDate reference, LocalDate candidate) {
        return reference.isAfter(candidate) || reference.isEqual(candidate);
    }
}
