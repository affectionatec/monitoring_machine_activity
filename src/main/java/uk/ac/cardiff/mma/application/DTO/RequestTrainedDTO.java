package uk.ac.cardiff.mma.application.DTO;

public class RequestTrainedDTO {

    private int id;
    private String equipmentName;
    private String username;
    private String dateTrained;

    public RequestTrainedDTO(int id, String equipmentName, String username, String dateTrained) {
        this.id = id;
        this.equipmentName = equipmentName;
        this.username = username;
        this.dateTrained = dateTrained;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
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
