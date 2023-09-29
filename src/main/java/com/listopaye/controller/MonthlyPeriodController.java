package com.listopaye.controller;

import com.listopaye.domain.MonthlyPeriodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.listopaye.controller.MonthlyPeriodIdRepresentation.monthlyPeriodIdOf;

@RestController
@RequestMapping("/monthly-periods")
public class MonthlyPeriodController {

    private final MonthlyPeriodService monthlyPeriodService;

    public MonthlyPeriodController(MonthlyPeriodService monthlyPeriodService) {
        this.monthlyPeriodService = monthlyPeriodService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonthlyPeriodRepresentation> getMonthlyPeriodById(@PathVariable String id) {
        var monthlyPeriodId = monthlyPeriodIdOf(id);
        var monthlyPeriod = monthlyPeriodService.getMonthlyPeriod(monthlyPeriodId);
        return ResponseEntity.ok(MonthlyPeriodRepresentation.of(monthlyPeriod));
    }

}
