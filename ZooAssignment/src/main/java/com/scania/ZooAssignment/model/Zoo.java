package com.scania.ZooAssignment.modal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@XmlRootElement(name = "Zoo")
public class Zoo {
    private List<Lion> lions;
    private List<Giraffe> giraffes;
    private List<Tiger> tigers;
    private List<Zebra> zebras;
    private List<Wolf> wolves;
    private List<Piranha> piranhas;

    @XmlElement(name = "Lions")
    public List<Lion> getLions() {
        return lions;
    }

    public void setLions(List<Lion> lions) {
        this.lions = lions;
    }

    @XmlElement(name = "Giraffes")
    public List<Giraffe> getGiraffes() {
        return giraffes;
    }

    public void setGiraffes(List<Giraffe> giraffes) {
        this.giraffes = giraffes;
    }

    @XmlElement(name = "Tigers")
    public List<Tiger> getTigers() {
        return tigers;
    }

    public void setTigers(List<Tiger> tigers) {
        this.tigers = tigers;
    }

    @XmlElement(name = "Zebras")
    public List<Zebra> getZebras() {
        return zebras;
    }

    public void setZebras(List<Zebra> zebras) {
        this.zebras = zebras;
    }

    @XmlElement(name = "Wolves")
    public List<Wolf> getWolves() {
        return wolves;
    }

    public void setWolves(List<Wolf> wolves) {
        this.wolves = wolves;
    }

    @XmlElement(name = "Piranhas")
    public List<Piranha> getPiranhas() {
        return piranhas;
    }

    public void setPiranhas(List<Piranha> piranhas) {
        this.piranhas = piranhas;
    }
}
