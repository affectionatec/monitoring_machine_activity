package uk.ac.cardiff.mma.application.equipment.entity;

import javax.persistence.*;

@Entity
@Table(name = "service_reminder")
public class ServiceReminderDTO {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String category;
    private String name;
    private boolean complete;

    public ServiceReminderDTO() {}

    public ServiceReminderDTO(String category, String name) {
        this.category = category;
        this.name = name;
        this.complete = false;
    }

    @Override
    public String toString() {
        return String.format(
                "TodoItem[id=%d, category='%s', name='%s', complete='%b']",
                id, category, name, complete);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        return;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
        return;
    }

     public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        return;
    }

    public boolean isComplete() {
        return complete;
    }
    
    public void setComplete(boolean complete) {
        this.complete = complete;
        return;
    }
}