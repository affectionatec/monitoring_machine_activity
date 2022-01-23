package uk.ac.cardiff.mma.application.DTO;

import org.springframework.format.annotation.DateTimeFormat;

public class ChemicalDTO {
    private int chemicalID;
    private String chemicalName;
    private double storage;
    private String level;
    private String location;
    private String unit;
    private int bottleNum;
    private String levelPicture;

    public ChemicalDTO(int chemicalID, String chemicalName, double storage, String level, String location, String unit, int bottleNum) {
        this.chemicalID = chemicalID;
        this.chemicalName = chemicalName;
        this.storage = storage;
        this.level = level;
        this.location = location;
        this.unit = unit;
        this.bottleNum = bottleNum;
    }

    public int getChemicalID() {
        return chemicalID;
    }

    public void setChemicalID(int chemicalID) {
        this.chemicalID = chemicalID;
    }

    public String getChemicalName() {
        return chemicalName;
    }

    public void setChemicalName(String chemicalName) {
        this.chemicalName = chemicalName;
    }

    public double getStorage() {
        return storage;
    }

    public void setStorage(double storage) {
        this.storage = storage;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getBottleNum() {
        return bottleNum;
    }

    public void setBottleNum(int bottleNum) {
        this.bottleNum = bottleNum;
    }

    public String getLevelPicture() {
        return levelPicture;
    }

    public void setLevelPicture(String levelPicture) {
        this.levelPicture = levelPicture;
    }
}
