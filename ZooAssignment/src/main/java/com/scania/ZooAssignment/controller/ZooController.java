package com.scania.ZooAssignment.controller;

import com.opencsv.exceptions.CsvException;
import jakarta.xml.bind.JAXBException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.scania.ZooAssignment.dto.ZooResponseDto;
import com.scania.ZooAssignment.service.ZooService;
import java.io.IOException;

@RestController
public class ZooController {

    private final ZooService zooService;

    public ZooController(ZooService zooService) {
        this.zooService = zooService;
    }

    @PostMapping("/api/zoo/cost")
    public ResponseEntity<ZooResponseDto> calculateCostPerDay(@RequestParam(value = "textFile") MultipartFile textFile,
            @RequestParam(value = "csvFile") MultipartFile csvFile,
            @RequestParam(value = "xmlFile") MultipartFile xmlFile) throws IOException, CsvException, JAXBException {
        return ResponseEntity.ok(zooService.calculateZooCostPerDay(textFile, csvFile, xmlFile));
    }

    @GetMapping("/api/zoo/cost")
    public ResponseEntity<ZooResponseDto> calculateCostPerDayDefault() throws IOException, CsvException, JAXBException {
        return ResponseEntity.ok(zooService.calculateZooCostPerDay(null, null, null));
    }
}
