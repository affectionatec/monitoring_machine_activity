package uk.ac.cardiff.mma.application.DTO;

public class InvoiceDTO {
    String equipmentName;
    int halfHours;
    float chargeRate;
    float totalCharge;

    public InvoiceDTO(String equipmentName, int halfHours, float chargeRate, float totalCharge) {
        this.equipmentName = equipmentName;
        this.halfHours = halfHours;
        this.chargeRate = chargeRate;
        this.totalCharge = totalCharge;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public int getHalfHours() {
        return halfHours;
    }

    public void setHalfHours(int halfHours) {
        this.halfHours = halfHours;
    }

    public float getChargeRate() {
        return chargeRate;
    }

    public void setChargeRate(float chargeRate) {
        this.chargeRate = chargeRate;
    }

    public float getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(float totalCharge) {
        this.totalCharge = totalCharge;
    }
}
