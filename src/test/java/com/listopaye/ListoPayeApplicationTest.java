package com.listopaye;

import com.listopaye.controller.NewPtoRepresentation;
import com.listopaye.controller.PtoRepresentation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ListoPayeApplicationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void create_a_pto() {
        // Given
        var theNewPto = NewPtoRepresentation.of("Bob");
        // When
        var response = restTemplate.postForEntity("/ptos", theNewPto, PtoRepresentation.class);
        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).satisfies(
                createdPto -> {
                    assertThat(createdPto.id()).isNotNull();
                    assertThat(createdPto.employeeName()).isEqualTo("Bob");
                }
        );
    }
}