package com.scania.ZooAssignment.model;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "Zoo")
@XmlAccessorType(XmlAccessType.FIELD)
public class Zoo {
    @XmlElementWrapper(name= "Lions")
    @XmlElement(name = "Lion")
    private List<Animal> lions;

    @XmlElementWrapper(name= "Giraffes")
    @XmlElement(name = "Giraffe")
    private List<Animal> giraffes;

    @XmlElementWrapper(name= "Tigers")
    @XmlElement(name = "Tiger")
    private List<Animal> tigers;

    @XmlElementWrapper(name= "Zebras")
    @XmlElement(name = "Zebra")
    private List<Animal> zebra;

    @XmlElementWrapper(name= "Wolves")
    @XmlElement(name = "Wolf")
    private List<Animal> wolves;

    @XmlElementWrapper(name= "Piranhas")
    @XmlElement(name = "Piranha")
    private List<Animal> piranha;

    public Zoo(){
    }

    public Zoo(List<Animal> lions, List<Animal> giraffes, List<Animal> tigers, List<Animal> zebra, List<Animal> wolves, List<Animal> piranha) {
        this.lions = lions;
        this.giraffes = giraffes;
        this.tigers = tigers;
        this.zebra = zebra;
        this.wolves = wolves;
        this.piranha = piranha;
    }

    public List<Animal> getLions() {
        return lions;
    }

    public void setLions(List<Animal> lions) {
        this.lions = lions;
    }

    public List<Animal> getGiraffes() {
        return giraffes;
    }

    public void setGiraffes(List<Animal> giraffes) {
        this.giraffes = giraffes;
    }

    public List<Animal> getTigers() {
        return tigers;
    }

    public void setTigers(List<Animal> tigers) {
        this.tigers = tigers;
    }

    public List<Animal> getZebra() {
        return zebra;
    }

    public void setZebra(List<Animal> zebra) {
        this.zebra = zebra;
    }

    public List<Animal> getWolves() {
        return wolves;
    }

    public void setWolves(List<Animal> wolves) {
        this.wolves = wolves;
    }

    public List<Animal> getPiranha() {
        return piranha;
    }

    public void setPiranha(List<Animal> piranha) {
        this.piranha = piranha;
    }
}
