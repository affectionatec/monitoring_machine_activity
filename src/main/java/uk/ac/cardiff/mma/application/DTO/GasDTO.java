package uk.ac.cardiff.mma.application.DTO;

public class GasDTO {

    private int id;
    private String name;
    private int storage;
    private String unit;
    private int bottleNum;
    private String coshh;
    private String location;
    private String hazardLevel;
    private String comments;

    /* <init>() means that someone tried to call a constructor without any parameters.
    ** Adding a default constructor to solve this problem. */
    public GasDTO() {

    }

    public GasDTO(int id, String name, int storage, String unit, int bottleNum, String coshh, String location, String hazardLevel, String comments) {
        this.id = id;
        this.name = name;
        this.storage = storage;
        this.unit = unit;
        this.bottleNum = bottleNum;
        this.coshh = coshh;
        this.location = location;
        this.hazardLevel = hazardLevel;
        this.comments = comments;
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

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
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

    public String getCoshh() {
        return coshh;
    }

    public void setCoshh(String coshh) {
        this.coshh = coshh;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHazardLevel() {
        return hazardLevel;
    }

    public void setHazardLevel(String hazardLevel) {
        this.hazardLevel = hazardLevel;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}