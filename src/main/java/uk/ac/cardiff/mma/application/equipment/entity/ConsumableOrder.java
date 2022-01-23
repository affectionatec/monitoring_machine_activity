package uk.ac.cardiff.mma.application.equipment.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "consumable_order")
public class ConsumableOrder {

    @Column
    private int consumable_id;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderID;

    @Column
    private int quantity;

    @Column
    private Date orderCreatedOn;

    @Column
    private String name;


    @Column
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ConsumableOrder{" +
                "consumable_id=" + consumable_id +
                ", orderID=" + orderID +
                ", quantity=" + quantity +
                ", orderCreatedOn=" + orderCreatedOn +
                ", name='" + name + '\'' +
                '}';
    }

    public int getConsumable_id() {
        return consumable_id;
    }

    public void setConsumable_id(int consumable_id) {
        this.consumable_id = consumable_id;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        orderID = orderID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getOrderCreatedOn() {
        return orderCreatedOn;
    }

    public void setOrderCreatedOn(Date orderCreatedOn) {
        this.orderCreatedOn = orderCreatedOn;
    }


    public ConsumableOrder(int consumable_id, int quantity, String name){
        super();
        this.consumable_id=consumable_id;
        this.quantity = quantity;
        this.name = name;
    }

    public ConsumableOrder(){

    }


}
