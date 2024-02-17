package com.scania.ZooAssignment.controller;

import com.opencsv.exceptions.CsvException;
import jakarta.xml.bind.JAXBException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.scania.ZooAssignment.service.ZooService;
import java.io.IOException;

@RestController
public class ZooController {

    private ZooService zooService;

    public ZooController(ZooService zooService) {
        this.zooService = zooService;
    }

    @GetMapping("/api/v1/zoo/cost")
    public Float calculateCostPerDay() throws IOException, CsvException, JAXBException {
        return zooService.calculateZooCostPerDay();
    }
}
