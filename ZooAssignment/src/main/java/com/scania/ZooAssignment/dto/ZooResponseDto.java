package com.scania.ZooAssignment.dto;

public record ZooResponseDto(
    Float costForLion,
    Float costForGiraffe,
    Float costForTiger,
    Float costForZebra,
    Float costForWolf,
    Float costForPiranha,
    Float totalCostOfFoodPerDay) {

}
