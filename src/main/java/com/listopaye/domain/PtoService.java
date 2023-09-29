package com.listopaye.domain;

import com.listopaye.domain.spi.PtoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PtoService {

    private final PtoRepository ptoRepository;
    private final MonthlyPeriodService monthlyPeriodService;

    public PtoService(PtoRepository ptoRepository, MonthlyPeriodService monthlyPeriodService) {
        this.ptoRepository = ptoRepository;
        this.monthlyPeriodService = monthlyPeriodService;
    }

    public Pto createPto(NewPto newPto) {
        var pto = Pto.of(UUID.randomUUID(), newPto.employeeName(), newPto.startDate(), newPto.endDate());
        ptoRepository.create(pto);

        pto.monthlyPeriodPtos().forEach(
                monthlyPto -> monthlyPeriodService.addPto(monthlyPto)
        );

        return pto;
    }

    public Pto getPtoById(UUID id) {
        return ptoRepository.getById(id);
    }
}
