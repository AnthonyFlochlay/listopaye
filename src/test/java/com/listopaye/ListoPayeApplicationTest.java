package com.listopaye;

import com.listopaye.controller.NewPtoRepresentation;
import com.listopaye.controller.PtoRepresentation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ListoPayeApplicationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void create_a_single_day_pto() {
        // Given
        var theNewPto = NewPtoRepresentation.ofSingleDay(
                "Bob",
                LocalDate.of(2023, Month.APRIL.getValue(), 8)
        );
        // When
        var response = restTemplate.postForEntity("/ptos", theNewPto, PtoRepresentation.class);
        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).satisfies(
                createdPto -> {
                    assertThat(createdPto.id()).isNotNull();
                    assertThat(createdPto.employeeName()).isEqualTo("Bob");
                    assertThat(createdPto.startDate()).isEqualTo(theNewPto.startDate());
                    assertThat(createdPto.endDate()).isEqualTo(theNewPto.endDate());
                }
        );
    }

    @Test
    void create_a_multi_days_pto() {
        // Given
        var theNewPto = NewPtoRepresentation.ofMultiDays(
                "Bob",
                LocalDate.of(2023, Month.APRIL.getValue(), 8),
                LocalDate.of(2023, Month.APRIL.getValue(), 12)
        );
        // When
        var response = restTemplate.postForEntity("/ptos", theNewPto, PtoRepresentation.class);
        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).satisfies(
                createdPto -> {
                    assertThat(createdPto.id()).isNotNull();
                    assertThat(createdPto.employeeName()).isEqualTo("Bob");
                    assertThat(createdPto.startDate()).isEqualTo(theNewPto.startDate());
                    assertThat(createdPto.endDate()).isEqualTo(theNewPto.endDate());
                }
        );
    }

    @Test
    void get_an_existing_pto() {
        // Given
        var createdPto = createPto(NewPtoRepresentation.ofSingleDay("Bob", LocalDate.of(2023, Month.APRIL.getValue(), 8))).getBody();
        // When
        ResponseEntity<PtoRepresentation> response = restTemplate.getForEntity("/ptos/" + createdPto.id(), PtoRepresentation.class);
        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).satisfies(
                readPto -> assertThat(readPto).isEqualTo(createdPto)
        );
    }

    private ResponseEntity<PtoRepresentation> createPto(NewPtoRepresentation theNewPto) {
        return restTemplate.postForEntity("/ptos", theNewPto, PtoRepresentation.class);
    }
}