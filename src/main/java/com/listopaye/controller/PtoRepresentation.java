package com.listopaye.controller;

import com.listopaye.domain.Pto;

import java.time.LocalDate;
import java.util.UUID;

public record PtoRepresentation(UUID id, String employeeName, LocalDate startDate, LocalDate endDate) {
    public static PtoRepresentation of(Pto pto) {
        return new PtoRepresentation(pto.id(), pto.employeeName(), pto.startDate(), pto.endDate());
    }
}
