package com.scania.ZooAssignment.model;


import jakarta.xml.bind.annotation.XmlAttribute;

public class Animal {

    private String name;
    private Float kg;
    private String type;

    public Animal(){
    }

    public Animal(String name, Float kg, String type) {
        this.name = name;
        this.kg = kg;
        this.type = type;
    }

    @XmlAttribute(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute(name="kg")
    public Float getKg() {
        return kg;
    }

    public void setKg(Float kg) {
        this.kg = kg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
