package com.listopaye.controller;

import com.listopaye.domain.NewPto;

public record NewPtoRepresentation(String employeeName) {
    public static NewPtoRepresentation of(String employeeName) {
        return new NewPtoRepresentation(employeeName);
    }

    public NewPto toDomain() {
        return NewPto.of(employeeName);
    }
}
