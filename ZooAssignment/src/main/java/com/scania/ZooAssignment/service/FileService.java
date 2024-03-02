package com.scania.ZooAssignment.service;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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
        Path filePath = getResourceFilePath(fileName);
        try (var lines = Files.lines(filePath, StandardCharsets.UTF_8)) {
            return lines.map(line -> line.split("\\s*=\\s*", 2))
                        .collect(Collectors.toMap(
                                data -> data[0].toLowerCase(),
                                data -> Float.parseFloat(data[1])));
        } catch (IOException e) {
            throw new IOException("Error reading file: " + fileName, e);
        }
    }

    public List<AnimalDiet> populateAnimalDietFromCsv(MultipartFile csvFile) throws IOException, CsvException {
        String fileName = getFileName(csvFile, STATIC_FILE_NAMES[1]);
        Path filePath = getResourceFilePath(fileName);
        try (var reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            return new CsvToBeanBuilder<AnimalDiet>(reader)
                        .withType(AnimalDiet.class)
                        .withSeparator(';')
                        .build()
                        .parse();
        } catch (IOException e) {
            throw new IOException("Error reading file: " + fileName, e);
        }
    }

    public Zoo populateZooAnimalsFromXml(MultipartFile xmlFile) throws IOException, JAXBException {
        String fileName = getFileName(xmlFile, STATIC_FILE_NAMES[2]);
        Path filePath = getResourceFilePath(fileName);
        try (var reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            JAXBContext jaxbContext = JAXBContext.newInstance(Zoo.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (Zoo) jaxbUnmarshaller.unmarshal(reader);
        } catch (IOException e) {
            throw new IOException("Error reading file: " + fileName, e);
        }
    }

    private String getFileName(MultipartFile file, String defaultFileName) {
        return file != null ? file.getOriginalFilename() : defaultFileName;
    }

    private Path getResourceFilePath(String fileName) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + fileName);
        return resource.getFile().toPath();
    }
}
