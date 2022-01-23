package uk.ac.cardiff.mma.application.equipment.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Equipment")
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "inventory", nullable = false)
    private int inventory;
    @Column(name = "charge_rate")
    private float charge_rate;

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

    public float getCharge_rate() {
        return charge_rate;
    }

    public void setCharge_rate(float charge_rate) {
        this.charge_rate = charge_rate;
    }
}
