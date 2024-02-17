package com.scania.ZooAssignment.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ZooControllerTest {
    @Autowired
    RestTemplate restTemplate;
    @Value("${server.port}")
    private int port;

    @Test
    void testToGetTotalFoodCostPerDay() {
        String uri = "http://localhost:%s/api/zoo/cost".formatted(port);
        ResponseEntity<Float> exchange = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<Float>() {
                });

        Assertions.assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(exchange.getBody()).isNotNull();
    }

}
