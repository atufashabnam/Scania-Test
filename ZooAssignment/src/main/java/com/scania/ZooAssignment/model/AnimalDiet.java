package com.scania.ZooAssignment.model;

import com.opencsv.bean.CsvBindByPosition;

public class AnimalDiet {

    @CsvBindByPosition(position = 0)
    private String type;

    @CsvBindByPosition(position = 1)
    private Float foodPerWeight;

    @CsvBindByPosition(position = 2)
    private String foodType;

    @CsvBindByPosition(position = 3)
    private String percentageOfMeat;

    public AnimalDiet(){
    }

    public AnimalDiet(String type, Float foodPerWeight, String foodType, String percentageOfMeat) {
        this.type = type;
        this.foodPerWeight = foodPerWeight;
        this.foodType = foodType;
        this.percentageOfMeat = percentageOfMeat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getFoodPerWeight() {
        return foodPerWeight;
    }

    public void setFoodPerWeight(Float foodPerWeight) {
        this.foodPerWeight = foodPerWeight;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getPercentageOfMeat() {
        return percentageOfMeat;
    }

    public void setPercentageOfMeat(String percentageOfMeat) {
        this.percentageOfMeat = percentageOfMeat;
    }
}
