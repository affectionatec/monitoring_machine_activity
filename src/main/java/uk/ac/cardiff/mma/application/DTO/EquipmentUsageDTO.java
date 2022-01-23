package uk.ac.cardiff.mma.application.DTO;

public class EquipmentUsageDTO {


    String bookedDate;
    int times;

    public EquipmentUsageDTO(String bookedDate, int times) {

        this.bookedDate = bookedDate;
        this.times = times;

    }

    public String getBookedDate() {
        return bookedDate;
    }

    public void setBookedDate(String bookedDate) {
        this.bookedDate = bookedDate;
    }

    public int getTimes(){
        return times;
    }
    public void setTimes(int times){
        this.times=times;
    }
}
