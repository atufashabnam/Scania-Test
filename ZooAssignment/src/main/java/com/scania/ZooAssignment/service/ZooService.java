package com.scania.ZooAssignment.service;

import com.opencsv.exceptions.CsvException;
import com.scania.ZooAssignment.dto.ZooResponseDto;
import jakarta.xml.bind.JAXBException;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface ZooService {
    public ZooResponseDto calculateZooCostPerDay(MultipartFile textFile, MultipartFile csvFile, MultipartFile xmlFile)
            throws IOException, CsvException, JAXBException;
}
