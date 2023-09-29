package com.listopaye.controller;

import com.listopaye.domain.Pto;

import java.util.UUID;

public record PtoRepresentation(UUID id, String employeeName) {
    public static PtoRepresentation of(Pto pto) {
        return new PtoRepresentation(pto.id(), pto.employeeName());
    }
}
