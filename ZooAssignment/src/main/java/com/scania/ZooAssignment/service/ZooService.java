package com.scania.ZooAssignment.service;

import com.opencsv.exceptions.CsvException;
import jakarta.xml.bind.JAXBException;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface ZooService {
    public Float calculateZooCostPerDay(MultipartFile textFile, MultipartFile csvFile, MultipartFile xmlFile) throws IOException, CsvException, JAXBException;
}
