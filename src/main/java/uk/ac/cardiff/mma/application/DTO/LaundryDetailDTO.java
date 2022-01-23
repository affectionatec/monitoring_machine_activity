package uk.ac.cardiff.mma.application.DTO;

public class LaundryDetailDTO {


    String laundry_id;
    int id;
    String barcode;
    String comment;
    String size;
    String status;
    String item;



    public LaundryDetailDTO(String size,String status,int id) {
       this.size=size;
       this.status=status;
       this.id=id;


    }
    public LaundryDetailDTO(String item,String size,String status,int id) {
        this.item=item;
        this.size=size;
        this.status=status;
        this.id=id;


    }

    public LaundryDetailDTO(String laundry_id,String barcode,String status,String comment) {

        this.laundry_id=laundry_id;
        this.barcode=barcode;
        this.status=status;
        this.comment=comment;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getItem(){
        return item;
    }
    public void setItem(String item){
        this.item=item;
    }
    public String getLaundry_id() {
        return laundry_id;
    }

    public void setLaundry_id(String laundry_id) {
        this.laundry_id = laundry_id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String hood_size) {
        this.size = size;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
