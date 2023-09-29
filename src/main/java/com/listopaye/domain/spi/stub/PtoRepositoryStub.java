package com.listopaye.domain.spi.stub;

import com.listopaye.domain.Pto;
import com.listopaye.domain.spi.PtoRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class PtoRepositoryStub implements PtoRepository {
    private Map<UUID, Pto> ptos = new HashMap<>();

    @Override
    public Pto getById(UUID id) {
        return ptos.get(id);
    }

    @Override
    public void create(Pto pto) {
        ptos.put(pto.id(), pto);
    }
}
