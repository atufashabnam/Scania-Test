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
import org.springframework.web.multipart.MultipartFile;

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

    private static final String STATIC_PRICES_FILE = "prices.txt";
    private static final String STATIC_ANIMALS_FILE = "animals.csv";
    private static final String STATIC_ZOO_FILE = "zoo.xml";

    public FileService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    Map<String, Float> loadPricesFromFile(MultipartFile textFile) throws IOException {
        InputStream inputStream = null;
        if(textFile != null){
            inputStream = textFile.getInputStream();
        }else{
            Resource resource = resourceLoader.getResource("classpath:" + STATIC_PRICES_FILE);
            inputStream = resource.getInputStream();
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines()
                    .map(line -> line.split("\\s*=\\s*", 2))
                    .collect(Collectors.toMap(
                            data -> data[0].toLowerCase(),
                            data -> Float.parseFloat(data[1])));
        } 
    }

    public List<AnimalDiet> populateAnimalDietFromCsv(MultipartFile csvFile) throws IOException, CsvException {
        InputStream inputStream = null;
        if(csvFile != null){
            inputStream = csvFile.getInputStream();
        }else{
            Resource resource = resourceLoader.getResource("classpath:" + STATIC_ANIMALS_FILE);
            inputStream = resource.getInputStream();
            
        }
        return new CsvToBeanBuilder<AnimalDiet>(new InputStreamReader(inputStream))
                .withType(AnimalDiet.class)
                .withSeparator(';')
                .build()
                .parse();
    }

    public Zoo populateZooAnimalsFromXml(MultipartFile xmlFile) throws IOException, JAXBException {
        InputStream inputStream = null;
        if(xmlFile != null){
            inputStream = xmlFile.getInputStream();
        }else{
            Resource resource = resourceLoader.getResource("classpath:" + STATIC_ZOO_FILE);
            inputStream = resource.getInputStream();
            
        }
        JAXBContext jaxbContext = JAXBContext.newInstance(Zoo.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (Zoo) jaxbUnmarshaller.unmarshal(new InputStreamReader(inputStream));
    }

}
