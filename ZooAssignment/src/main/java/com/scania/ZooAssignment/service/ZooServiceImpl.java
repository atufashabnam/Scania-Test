package com.scania.ZooAssignment.service;

import com.opencsv.exceptions.CsvException;
import com.scania.ZooAssignment.dto.ZooResponseDto;
import com.scania.ZooAssignment.model.Animal;
import com.scania.ZooAssignment.model.AnimalDiet;
import com.scania.ZooAssignment.model.Zoo;
import jakarta.xml.bind.JAXBException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ZooServiceImpl implements ZooService {

    private static final String LION = "Lion";
    private static final String TIGER = "Tiger";
    private static final String GIRAFFE = "Giraffe";
    private static final String WOLF = "Wolf";
    private static final String ZEBRA = "Zebra";
    private static final String PIRANHA = "Piranha";
    private static final String FRUIT = "fruit";
    private static final String MEAT = "meat";
    private static final String BOTH = "both";

    private final FileService fileService;

    public ZooServiceImpl(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public ZooResponseDto calculateZooCostPerDay(MultipartFile textFile, MultipartFile csvFile, MultipartFile xmlFile)
            throws IOException, CsvException, JAXBException {
        Map<String, Float> prices = fileService.loadPricesFromFile(textFile);
        List<AnimalDiet> diets = fileService.populateAnimalDietFromCsv(csvFile);
        Zoo zooAnimalList = fileService.populateZooAnimalsFromXml(xmlFile);
        Map<String, AnimalDiet> animalDietMap = convertAnimalDietToMap(diets);
        return calculateCost(prices, animalDietMap, zooAnimalList);
    }

    private Map<String, AnimalDiet> convertAnimalDietToMap(List<AnimalDiet> diets) {
        return diets.stream().collect(Collectors.toMap(AnimalDiet::getType, animal -> animal));
    }

    private ZooResponseDto calculateCost(Map<String, Float> prices, Map<String, AnimalDiet> animalDietMap,
            Zoo zooAnimals) {
        Float costForLion = calculateCostForAnimalType(prices, animalDietMap.get(LION), zooAnimals.getLions());
        Float costForGiraffe = calculateCostForAnimalType(prices, animalDietMap.get(GIRAFFE), zooAnimals.getGiraffes());
        Float costForTiger = calculateCostForAnimalType(prices, animalDietMap.get(TIGER), zooAnimals.getTigers());
        Float costForZebra = calculateCostForAnimalType(prices, animalDietMap.get(ZEBRA), zooAnimals.getZebras());
        Float costForWolf = calculateCostForAnimalType(prices, animalDietMap.get(WOLF), zooAnimals.getWolves());
        Float costForPiranha = calculateCostForAnimalType(prices, animalDietMap.get(PIRANHA), zooAnimals.getPiranhas());
        Float total = costForLion + costForGiraffe + costForTiger + costForZebra + costForWolf + costForPiranha;
        ZooResponseDto zooResponseDto = new ZooResponseDto(costForLion, costForGiraffe, costForTiger, costForZebra,
                costForWolf, costForPiranha, total);
        return zooResponseDto;
    }

    private Float calculateCostForAnimalType(Map<String, Float> prices, AnimalDiet diet, List<Animal> animals) {
        if (diet == null)
            return 0F;

        Float totalCost = 0F;
        Float foodPerWeight = diet.getFoodPerWeight();
        String foodType = diet.getFoodType();
        Float percentage = parsePercentage(diet.getPercentageOfMeat());

        for (Animal animal : animals) {
            if (BOTH.equals(foodType)) {
                Float fruitFoodPrice = prices.get(FRUIT);
                Float meatFoodPrice = prices.get(MEAT);
                Float bodyWeightForMeat = animal.getKg() * percentage;
                Float bodyWeightForFruit = animal.getKg() - bodyWeightForMeat;
                totalCost += meatFoodPrice * bodyWeightForMeat * foodPerWeight;
                totalCost += fruitFoodPrice * bodyWeightForFruit * foodPerWeight;
            } else {
                Float foodPrice = prices.get(foodType);
                totalCost += foodPrice * animal.getKg() * foodPerWeight;
            }
        }
        return totalCost;
    }

    private Float parsePercentage(String percentage) {
        return percentage.isEmpty() ? 1F : Float.parseFloat(percentage.replace("%", "")) / 100F;
    }
}
