package uk.ac.cardiff.mma.application.DTO;

public class CheckItemsDTO {
    private int id;
    private String name;
    private String mode;
    private String value;

    public CheckItemsDTO(int id, String name, String mode, String value) {
        this.id = id;
        this.name = name;
        this.mode = mode;
        this.value = value;
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

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
