package com.scania.ZooAssignment.service;

import com.opencsv.exceptions.CsvException;
import jakarta.xml.bind.JAXBException;
import java.io.IOException;

public interface ZooService {
    public double calculateCost() throws IOException, CsvException, JAXBException;

}

