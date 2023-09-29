package com.listopaye.controller;

import com.listopaye.domain.PtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@RestController
@RequestMapping("/ptos")
public class PtoController {

    private final PtoService ptoService;

    public PtoController(PtoService ptoService) {
        this.ptoService = ptoService;
    }

    @PostMapping
    public ResponseEntity<PtoRepresentation> createPto(@RequestBody NewPtoRepresentation newPto) {
        var createdPto = PtoRepresentation.of(
                ptoService.createPto(newPto.toDomain())
        );
        return ResponseEntity
                .created(fromMethodCall(on(this.getClass()).getPtoById(createdPto.id())).build().toUri())
                .body(createdPto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PtoRepresentation> getPtoById(@PathVariable UUID id) {
        var pto = ptoService.getPtoById(id);
        return ResponseEntity.ok(PtoRepresentation.of(pto));
    }


}
