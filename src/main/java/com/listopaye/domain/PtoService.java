package com.listopaye.domain;

import com.listopaye.domain.spi.PtoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PtoService {

    private final PtoRepository ptoRepository;

    public PtoService(PtoRepository ptoRepository) {
        this.ptoRepository = ptoRepository;
    }

    public Pto createPto(NewPto newPto) {
        var pto = Pto.of(UUID.randomUUID(), newPto.employeeName());
        ptoRepository.create(pto);
        return pto;
    }

    public Pto getPtoById(UUID id) {
        return ptoRepository.getById(id);
    }
}
