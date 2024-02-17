package com.scania.ZooAssignment.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import com.scania.ZooAssignment.model.AnimalDiet;
import com.scania.ZooAssignment.model.Zoo;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

@Service
public class FileService {
    private final ResourceLoader resourceLoader;

    public FileService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    Map<String, Float> loadPricesFromFile() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + "prices.txt");
        InputStream inputStream = resource.getInputStream();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines()
                    .map(line -> line.split("\\s*=\\s*", 2))
                    .collect(Collectors.toMap(
                            data -> data[0],
                            data -> Float.parseFloat(data[1])));
        } catch (Exception ex) {
            throw new IOException("Exceptionwhile processing Price file");
        }
    }

    public List<AnimalDiet> populateAnimalDietFromCsv() throws IOException, CsvException {
        Resource resource = resourceLoader.getResource("classpath:" + "animals.csv");
        return new CsvToBeanBuilder<AnimalDiet>(new InputStreamReader(resource.getInputStream()))
                .withType(AnimalDiet.class)
                .withSeparator(';')
                .build()
                .parse();
    }

    public Zoo populateZooAnimalsFromXml() throws IOException, JAXBException {
        Resource resource = resourceLoader.getResource("classpath:" + "zoo.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(Zoo.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (Zoo) jaxbUnmarshaller.unmarshal(new InputStreamReader(resource.getInputStream()));
    }

}