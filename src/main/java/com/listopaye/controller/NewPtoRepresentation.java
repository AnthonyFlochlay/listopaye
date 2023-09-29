package com.listopaye.controller;

import com.listopaye.domain.NewPto;

import java.time.LocalDate;

public record NewPtoRepresentation(String employeeName, LocalDate startDate, LocalDate endDate) {
    public static NewPtoRepresentation ofSingleDay(String employeeName, LocalDate day) {
        return new NewPtoRepresentation(employeeName, day, day.plusDays(1));
    }

    public static NewPtoRepresentation ofMultiDays(String employeeName, LocalDate startDate, LocalDate endDateIncluded) {
        return new NewPtoRepresentation(employeeName, startDate, endDateIncluded.plusDays(1));
    }

    public NewPto toDomain() {
        return NewPto.of(employeeName, startDate, endDate);
    }
}
