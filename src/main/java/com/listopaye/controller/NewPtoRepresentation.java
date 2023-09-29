package com.listopaye.controller;

import com.listopaye.domain.NewPto;

import java.time.LocalDate;

public record NewPtoRepresentation(String employeeName, LocalDate startDate, LocalDate endDate) {
    public static NewPtoRepresentation ofSingleDay(String employeeName, LocalDate localDate) {
        return new NewPtoRepresentation(employeeName, localDate, localDate.plusDays(1));
    }

    public NewPto toDomain() {
        return NewPto.of(employeeName, startDate, endDate);
    }
}
