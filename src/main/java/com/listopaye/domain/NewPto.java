package com.listopaye.domain;

public record NewPto(String employeeName) {
    public static NewPto of(String employeeName) {
        return new NewPto(employeeName);
    }
}
