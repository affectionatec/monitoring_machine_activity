package uk.ac.cardiff.mma.application.DTO;


public class AddingChemicalDTO {
    private String chemicalName;
    private String level;
    private String location;
    private double storage;
    private String unit;
    private int bottleNum;

    public AddingChemicalDTO(String chemicalName, String level, String location, double storage, String unit, int bottleNum) {
        this.chemicalName = chemicalName;
        this.level = level;
        this.location = location;
        this.storage = storage;
        this.unit = unit;
        this.bottleNum = bottleNum;
    }

    public String getChemicalName() {
        return chemicalName;
    }

    public void setChemicalName(String chemicalName) {
        this.chemicalName = chemicalName;
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

    public double getStorage() {
        return storage;
    }

    public void setStorage(double storage) {
        this.storage = storage;
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
}
