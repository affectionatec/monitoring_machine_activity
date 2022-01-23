package uk.ac.cardiff.mma.application.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class GasDeliveryDTO {

    private int deliveryID;
    private int gasID;
    // Passing the date into the database is formatted in this form.
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deliveryDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expiryDate;
    private int distributionWeight;
    private String deliveryStaff;

    public GasDeliveryDTO(int deliveryID, int gasID, Date deliveryDate, Date expiryDate, int distributionWeight, String deliveryStaff) {
        this.deliveryID = deliveryID;
        this.gasID = gasID;
        this.deliveryDate = deliveryDate;
        this.expiryDate = expiryDate;
        this.distributionWeight = distributionWeight;
        this.deliveryStaff = deliveryStaff;
    }

    public int getDeliveryID() {
        return deliveryID;
    }

    public void setDeliveryID(int deliveryID) {
        this.deliveryID = deliveryID;
    }

    public int getGasID() {
        return gasID;
    }

    public void setGasID(int gasID) {
        this.gasID = gasID;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getDistributionWeight() {
        return distributionWeight;
    }

    public void setDistributionWeight(int distributionWeight) {
        this.distributionWeight = distributionWeight;
    }

    public String getDeliveryStaff() {
        return deliveryStaff;
    }

    public void setDeliveryStaff(String deliveryStaff) {
        this.deliveryStaff = deliveryStaff;
    }

}