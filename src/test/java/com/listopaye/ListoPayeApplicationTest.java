package com.listopaye;

import com.listopaye.controller.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static com.listopaye.DateFixtures.thisYear;
import static com.listopaye.controller.NewPtoRepresentation.ofMultiDays;
import static com.listopaye.controller.NewPtoRepresentation.ofSingleDay;
import static java.time.Month.MARCH;
import static java.time.Month.MAY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ListoPayeApplicationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void create_a_single_day_pto() {
        // Given
        var theNewPto = ofSingleDay(
                "Bob",
                LocalDate.of(2023, Month.APRIL.getValue(), 8)
        );
        // When
        var response = restTemplate.postForEntity("/ptos", theNewPto, PtoRepresentation.class);
        // Then
        assertPtoIsCreatedAndEquals(response, theNewPto);
    }

    private static void assertPtoIsCreatedAndEquals(ResponseEntity<PtoRepresentation> response, NewPtoRepresentation expectedPto) {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).satisfies(
                createdPto -> {
                    assertThat(createdPto.id()).isNotNull();
                    assertThat(createdPto.employeeName()).isEqualTo("Bob");
                    assertThat(createdPto.startDate()).isEqualTo(expectedPto.startDate());
                    assertThat(createdPto.endDate()).isEqualTo(expectedPto.endDate());
                }
        );
    }

    @Test
    void create_a_multi_days_pto() {
        // Given
        var theNewPto = ofMultiDays(
                "Bob",
                LocalDate.of(2023, Month.APRIL.getValue(), 8),
                LocalDate.of(2023, Month.APRIL.getValue(), 12)
        );
        // When
        var response = restTemplate.postForEntity("/ptos", theNewPto, PtoRepresentation.class);
        // Then
        assertPtoIsCreatedAndEquals(response, theNewPto);
    }

    @Test
    void get_an_existing_pto() {
        // Given
        var createdPto = createPto(ofSingleDay("Bob", LocalDate.of(2023, Month.APRIL.getValue(), 8)));
        // When
        ResponseEntity<PtoRepresentation> response = restTemplate.getForEntity("/ptos/" + createdPto.id(), PtoRepresentation.class);
        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).satisfies(
                readPto -> assertThat(readPto).isEqualTo(createdPto)
        );
    }

    private ResponseEntity<PtoRepresentation> createPtoResponse(NewPtoRepresentation theNewPto) {
        return restTemplate.postForEntity("/ptos", theNewPto, PtoRepresentation.class);
    }

    //TODO: get a non existing pto should return 404

    @Test
    void get_empty_monthly_period() {
        // Given
        int theYear = thisYear();
        var theMonth = Month.APRIL;
        var theMonthlyPeriodId = MonthlyPeriodIdRepresentation.of(theYear, theMonth);
        // When
        var response = restTemplate.getForEntity("/monthly-periods/" + theMonthlyPeriodId, MonthlyPeriodRepresentation.class);
        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(
                MonthlyPeriodRepresentation.of(
                        theMonthlyPeriodId,
                        theYear,
                        theMonth,
                        List.of()
                ));
    }

    //TODO: get unexisting monthly period (wrong period id) should return 404

    @Test
    void get_monthly_period_without_ptos() {
        // Given
        int theYear = thisYear();
        var theMonth = Month.APRIL;
        var theMonthlyPeriodId = MonthlyPeriodIdRepresentation.of(theYear, theMonth);
        // When
        var response = restTemplate.getForEntity("/monthly-periods/" + theMonthlyPeriodId, MonthlyPeriodRepresentation.class);
        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(
                MonthlyPeriodRepresentation.of(
                        theMonthlyPeriodId,
                        theYear,
                        theMonth,
                        List.of()
                ));
    }

    @Test
    void get_monthly_period_with_single_day_pto() {
        // Given
        int theYear = thisYear();
        var theMonth = Month.APRIL;
        var theMonthlyPeriodId = MonthlyPeriodIdRepresentation.of(theYear, theMonth);
        var thePtoDay = LocalDate.of(theYear, theMonth, 3);
        var thePto = createPto(ofSingleDay("employee1", thePtoDay));

        // When
        var response = restTemplate.getForEntity("/monthly-periods/" + theMonthlyPeriodId, MonthlyPeriodRepresentation.class);
        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(
                MonthlyPeriodRepresentation.of(
                        theMonthlyPeriodId,
                        theYear,
                        theMonth,
                        List.of(MonthlyPeriodPtoRepresentation.of(thePto.id(), "employee1", thePtoDay, thePtoDay.plusDays(1)))
                ));
    }

    @Test
    void get_monthly_period_with_a_lot_of_ptos() {
        // Given
        int theYear = thisYear();
        var theMonth = Month.APRIL;
        var theMonthlyPeriodId = MonthlyPeriodIdRepresentation.of(theYear, theMonth);
        var ptos = List.of(
                createPto(ofSingleDay("employee1", LocalDate.of(theYear, MARCH, 1))), // Should ignore ptos before period
                createPto(ofSingleDay("employee1", LocalDate.of(theYear, MAY, 2))), // Should ignore ptos after period
                createPto(ofSingleDay("employee1", LocalDate.of(theYear, theMonth, 3))), // should fetch single day PTO
                createPto(ofSingleDay("employee2", LocalDate.of(theYear, theMonth, 4))), // should fetch when several PTOs
                createPto(ofMultiDays("employee2", LocalDate.of(theYear, theMonth, 24), LocalDate.of(theYear, theMonth, 27))), // should fetch multi days PTO
                createPto(ofMultiDays("employee3", LocalDate.of(theYear, MARCH, 20), LocalDate.of(theYear, theMonth, 5))), // should fetch multi days PTO when starting before period
                createPto(ofMultiDays("employee4", LocalDate.of(theYear, theMonth, 20), LocalDate.of(theYear, MAY, 5))), // should fetch multi days PTO when ending after period
                createPto(ofMultiDays("employee5", LocalDate.of(theYear, MARCH, 20), LocalDate.of(theYear, MAY, 5))) // should fetch multi days PTO including period
        );
        // When
        var response = restTemplate.getForEntity("/monthly-periods/" + theMonthlyPeriodId, MonthlyPeriodRepresentation.class);
        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(
                MonthlyPeriodRepresentation.of(
                        theMonthlyPeriodId,
                        theYear,
                        theMonth,
                        List.of(
                                MonthlyPeriodPtoRepresentation.of(ptos.get(2).id(), "employee1", LocalDate.of(theYear, theMonth, 3), LocalDate.of(theYear, theMonth, 4)),
                                MonthlyPeriodPtoRepresentation.of(ptos.get(3).id(), "employee2", LocalDate.of(theYear, theMonth, 4), LocalDate.of(theYear, theMonth, 5)),
                                MonthlyPeriodPtoRepresentation.of(ptos.get(4).id(), "employee2", LocalDate.of(theYear, theMonth, 24), LocalDate.of(theYear, theMonth, 28)),
                                MonthlyPeriodPtoRepresentation.of(ptos.get(5).id(), "employee3", LocalDate.of(theYear, theMonth, 1), LocalDate.of(theYear, theMonth, 6)),
                                MonthlyPeriodPtoRepresentation.of(ptos.get(6).id(), "employee4", LocalDate.of(theYear, theMonth, 20), LocalDate.of(theYear, theMonth, 30)),
                                MonthlyPeriodPtoRepresentation.of(ptos.get(7).id(), "employee5", LocalDate.of(theYear, theMonth, 1), LocalDate.of(theYear, theMonth, 30))
                        )
                ));
    }

    private PtoRepresentation createPto(NewPtoRepresentation newPto) {
        ResponseEntity<PtoRepresentation> responseWithPto = createPtoResponse(newPto);
        assertThat(responseWithPto.getStatusCode().is2xxSuccessful()).isTrue();
        var pto = responseWithPto.getBody();
        assertThat(pto).isNotNull();
        return pto;
    }

}