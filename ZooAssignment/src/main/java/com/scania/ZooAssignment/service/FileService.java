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

    private static final String[] STATIC_FILE_NAMES = { "prices.txt", "animals.csv", "zoo.xml" };

    public FileService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    Map<String, Float> loadPricesFromFile(MultipartFile textFile) throws IOException {
        String fileName = getFileName(textFile, STATIC_FILE_NAMES[0]);
        try (InputStream inputStream = getFileInputStream(fileName)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                return reader.lines()
                        .map(line -> line.split("\\s*=\\s*", 2))
                        .collect(Collectors.toMap(
                                data -> data[0].toLowerCase(),
                                data -> Float.parseFloat(data[1])));           
        }
    }

    public List<AnimalDiet> populateAnimalDietFromCsv(MultipartFile csvFile) throws IOException, CsvException {
        String fileName = getFileName(csvFile, STATIC_FILE_NAMES[1]);
        try (InputStream inputStream = getFileInputStream(fileName)) {
            return new CsvToBeanBuilder<AnimalDiet>(new InputStreamReader(inputStream))
                    .withType(AnimalDiet.class)
                    .withSeparator(';')
                    .build()
                    .parse();
        }
    }

    public Zoo populateZooAnimalsFromXml(MultipartFile xmlFile) throws IOException, JAXBException {
        String fileName = getFileName(xmlFile, STATIC_FILE_NAMES[2]);
        try (InputStream inputStream = getFileInputStream(fileName)) {
            JAXBContext jaxbContext = JAXBContext.newInstance(Zoo.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (Zoo) jaxbUnmarshaller.unmarshal(new InputStreamReader(inputStream));
        }
    }

    private String getFileName(MultipartFile file, String defaultFileName) {
        return file != null ? file.getOriginalFilename() : defaultFileName;
    }

    private InputStream getFileInputStream(String fileName) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + fileName);
        return resource.getInputStream();
    }

}
