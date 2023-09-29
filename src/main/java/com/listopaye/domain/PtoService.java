package com.listopaye.domain;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PtoService {
    public Pto createPto(NewPto newPto) {
        return Pto.of(UUID.randomUUID(), newPto.employeeName());
    }

    public Pto getPtoById(UUID id) {
        return null;
    }
}
