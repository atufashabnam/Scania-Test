package com.scania.ZooAssignment.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.scania.ZooAssignment.service.ZooService;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class ZooControllerTest {
    @Autowired
    RestTemplate restTemplate;
    @Value("${server.port}")
    private int port;
    @Value("classpath:prices.txt")
    private Resource priceFile;
    @Value("classpath:animals.csv")
    private Resource animalFile;
    @Value("classpath:zoo.xml")
    private Resource ZooFile;

    @Value("classpath:empty_prices.txt")
    private Resource emptyPriceFile;
    @Value("classpath:empty_animals.csv")
    private Resource emptyAnimalFile;
    @Value("classpath:empty_zoo.xml")
    private Resource emptyZooFile;

    @Value("classpath:wrong_prices.txt")
    private Resource wrongPricesFile;

    @Mock
    private ZooService zooService;

    @InjectMocks
    private ZooController zooController;


    @Test
    void testToGetTotalFoodCostPerDay() throws Exception {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Float> exchange = restTemplate.exchange(
                "http://localhost:" + port + "/api/zoo/cost",
                HttpMethod.GET,
                requestEntity,
                Float.class);

        Assertions.assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(exchange.getBody()).isNotNull();
        Float cost = exchange.getBody();
        Assertions.assertThat(cost).isEqualTo(1609.0092F);
    }

    @Test
    void testToGetTotalFoodCostPerDayWithUploadedFiles() throws Exception {

        String uri = "http://localhost:%s/api/zoo/cost".formatted(port);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("textFile", priceFile);
        body.add("csvFile", animalFile);
        body.add("xmlFile", ZooFile);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Float> exchange = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Float>() {
                });

        Assertions.assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(exchange.getBody()).isNotNull();
        Float cost = exchange.getBody();
        Assertions.assertThat(cost).isEqualTo(1609.0092F);
    }

    @Test
    void testWithEmptyFiles() throws IOException {
        String uri = "http://localhost:%s/api/zoo/cost".formatted(port);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("textFile", emptyPriceFile);
        body.add("csvFile", emptyAnimalFile);
        body.add("xmlFile", emptyZooFile);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Float> exchange = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Float>() {
                });

        Assertions.assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(exchange.getBody()).isZero();
    }

  @Test
    void testWithNumberFormatException() throws IOException {
        String uri = "http://localhost:%s/api/zoo/cost".formatted(port);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("textFile", wrongPricesFile);
        body.add("csvFile", animalFile);
        body.add("xmlFile", ZooFile);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        HttpClientErrorException exception = null;
        try{
            restTemplate.exchange(
                uri,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Object>() {
                });
        }catch(HttpClientErrorException e){
            exception = e;
        }
        Assertions.assertThat(exception).isNotNull();
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

        String expectedErrorMessage = "For input string: \"asdfg\"";
        Assertions.assertThat(exception.getResponseBodyAsString().contains(expectedErrorMessage));
    }
}
