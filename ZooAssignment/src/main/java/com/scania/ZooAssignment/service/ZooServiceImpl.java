package com.scania.ZooAssignment.service;

import com.opencsv.exceptions.CsvException;
import com.scania.ZooAssignment.model.Animal;
import com.scania.ZooAssignment.model.AnimalDiet;
import com.scania.ZooAssignment.model.Zoo;
import jakarta.xml.bind.JAXBException;
import org.springframework.stereotype.Service;

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

    private FileService fileService;

    public ZooServiceImpl(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public Float calculateZooCostPerDay() throws IOException, CsvException, JAXBException {
        Map<String, Float> prices = fileService.loadPricesFromFile();
        List<AnimalDiet> diets = fileService.populateAnimalDietFromCsv();
        Zoo zooAnimalList = fileService.populateZooAnimalsFromXml();
        Map<String, AnimalDiet> animalDietMap = convertAnimalDietToMap(diets);
        return calculateCost(prices, animalDietMap, zooAnimalList);
    }

    private Map<String, AnimalDiet> convertAnimalDietToMap(List<AnimalDiet> diets) {
        Map<String, AnimalDiet> animalDietMap = diets.stream().collect(Collectors.toMap(
                animal -> animal.getType(), animal -> animal));
        return animalDietMap;
    }

    private Float calculateCost(Map<String, Float> prices, Map<String, AnimalDiet> animalDietMap, Zoo zooAnimals) {
        Float costForLion = iterateZooAnimalsAndCalculateCost(prices, animalDietMap.get(LION), zooAnimals.getLions());
        Float costForGiraffe = iterateZooAnimalsAndCalculateCost(prices, animalDietMap.get(GIRAFFE),
                zooAnimals.getGiraffes());
        Float costForTiger = iterateZooAnimalsAndCalculateCost(prices, animalDietMap.get(TIGER),
                zooAnimals.getTigers());
        Float costForZebra = iterateZooAnimalsAndCalculateCost(prices, animalDietMap.get(ZEBRA),
                zooAnimals.getZebras());
        Float costForWolf = iterateZooAnimalsAndCalculateCost(prices, animalDietMap.get(WOLF),
                zooAnimals.getWolves());
        Float costForPiranha = iterateZooAnimalsAndCalculateCost(prices, animalDietMap.get(PIRANHA),
                zooAnimals.getPiranhas());
        return costForLion + costForGiraffe + costForTiger + costForZebra + costForWolf + costForPiranha;
    }

    private Float iterateZooAnimalsAndCalculateCost(Map<String, Float> prices, AnimalDiet diet, List<Animal> animals) {
        Float totalCost = 0F;
        for (Animal animal : animals) {
            if (diet != null) {
                Float foodPerWeight = diet.getFoodPerWeight();
                String foodType = diet.getFoodType();
                Float percentage = parsePercentage(diet.getPercentageOfMeat());
                if (foodType.equals(BOTH)) {
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
        }
        return totalCost;
    }

    private Float parsePercentage(String percentage) {
        if (percentage.equals("")) {
            return 1F;
        } else {
            return Float.parseFloat(percentage.replace("%", "")) / 100F;
        }
    }

}
