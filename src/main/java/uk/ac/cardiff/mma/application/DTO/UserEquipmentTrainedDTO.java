package uk.ac.cardiff.mma.application.DTO;

public class UserEquipmentTrainedDTO {

    private int equipmentID;
    private String username;
    private String dateTrained;

    public UserEquipmentTrainedDTO(int equipmentID, String username, String dateTrained) {
        this.equipmentID = equipmentID;
        this.username = username;
        this.dateTrained = dateTrained;
    }

    public int getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(int equipmentID) {
        this.equipmentID = equipmentID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDateTrained() {
        return dateTrained;
    }

    public void setDateTrained(String dateTrained) {
        this.dateTrained = dateTrained;
    }
}

