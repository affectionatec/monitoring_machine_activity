package uk.ac.cardiff.mma.application.DTO;

public class EquipmentOperationDTO {
    int id;
    String date;
    String equipmentName;
    String itemName;
    String value ;


    public EquipmentOperationDTO(int id,String date,String equipmentName,String itemName, String value ) {
        this.id=id;
        this.equipmentName = equipmentName;
        this.date = date;
        this.itemName = itemName;
        this.value=value;

    }
    public EquipmentOperationDTO(String date){
        this.date=date;
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

    public void setEquipmentName(String name) {
        this.equipmentName = equipmentName;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }

    public void setItemName(String power) {
        this.itemName = itemName;
    }
    public String getItemName() {
        return itemName;
    }


    public String getValue() {
        return value;
    }
    public void setValue(String value){
        this.value=value;
    }










}
