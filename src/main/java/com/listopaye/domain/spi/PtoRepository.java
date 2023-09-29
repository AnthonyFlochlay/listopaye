package com.listopaye.domain.spi;

import com.listopaye.domain.Pto;

import java.util.UUID;

public interface PtoRepository {
    Pto getById(UUID id);

    void create(Pto pto);
}
