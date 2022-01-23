package uk.ac.cardiff.mma.application.DTO;

public class EquipmentDetailDTO {

    private int id;
    private String name;
    private int inventory;

    public EquipmentDetailDTO(int id, String name, int inventory) {
        this.id = id;
        this.name = name;
        this.inventory = inventory;
    }
    public EquipmentDetailDTO(int id, String name) {
        this.id = id;
        this.name = name;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

}
