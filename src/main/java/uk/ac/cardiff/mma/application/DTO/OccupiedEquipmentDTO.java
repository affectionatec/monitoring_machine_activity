package uk.ac.cardiff.mma.application.DTO;

public class OccupiedEquipmentDTO {
    int id;
    String equipmentName;
    String userName;
    String bookedDate;
    String time;
    int used;

    String project_code;

    public OccupiedEquipmentDTO(int id, String equipmentName, String userName, String bookedDate, String time, int used, String project_code) {
        this.id = id;
        this.equipmentName = equipmentName;
        this.userName = userName;
        this.bookedDate = bookedDate;
        this.time = time;
        this.used = used;
        this.project_code = project_code;
    }

    public OccupiedEquipmentDTO(int id, String equipmentName, String userName, String bookedDate, String time) {
        this.id = id;
        this.equipmentName = equipmentName;
        this.userName = userName;
        this.bookedDate = bookedDate;
        this.time = time;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBookedDate() {
        return bookedDate;
    }

    public void setBookedDate(String bookedDate) {
        this.bookedDate = bookedDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }

    public String getProject_code() {
        return project_code;
    }

    public void setProject_code(String project_code) {
        this.project_code = project_code;
    }
}

