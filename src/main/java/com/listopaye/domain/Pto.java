package com.listopaye.domain;

import java.util.UUID;

public record Pto(UUID id, String employeeName) {
    public static Pto of(UUID id, String employeeName) {
        return new Pto(id, employeeName);
    }
}
