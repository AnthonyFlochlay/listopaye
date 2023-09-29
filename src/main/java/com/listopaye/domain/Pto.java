package com.listopaye.domain;

import java.time.LocalDate;
import java.util.UUID;

public record Pto(UUID id, String employeeName, LocalDate startDate, LocalDate endDate) {
    public static Pto of(UUID id, String employeeName, LocalDate startDate, LocalDate endDate) {
        return new Pto(id, employeeName, startDate, endDate);
    }
}
