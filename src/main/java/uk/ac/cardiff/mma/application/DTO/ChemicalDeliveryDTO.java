package uk.ac.cardiff.mma.application.DTO;

import org.springframework.format.annotation.DateTimeFormat;

public class ChemicalDeliveryDTO {
    private int id;
    private String chemicalName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String deliveryDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String expiry;
    private double weight;
    private String unit;

    public ChemicalDeliveryDTO(int id, String chemicalName, String deliveryDate, String expiry, double weight, String unit) {
        this.id = id;
        this.chemicalName = chemicalName;
        this.deliveryDate = deliveryDate;
        this.expiry = expiry;
        this.weight = weight;
        this.unit = unit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChemicalName() {
        return chemicalName;
    }

    public void setChemicalName(String chemicalName) {
        this.chemicalName = chemicalName;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}