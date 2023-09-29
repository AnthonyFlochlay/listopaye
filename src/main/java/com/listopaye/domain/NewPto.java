package com.listopaye.domain;

import java.time.LocalDate;

public record NewPto(String employeeName, LocalDate startDate, LocalDate endDate) {

    public static NewPto of(String employeeName, LocalDate startDate, LocalDate endDate) {
        return new NewPto(employeeName, startDate, endDate);
    }
}
