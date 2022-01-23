package uk.ac.cardiff.mma.application.equipment.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@RequiredArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(nullable = false)
    private LocalDateTime deadLine = LocalDateTime.now().plusDays(1);

    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime createDate = LocalDateTime.now().plusDays(1);

    @Column(nullable = false)
    private Boolean done = false;


    public Task(long id, String title, String description, LocalDateTime deadLine, LocalDateTime createDate, Boolean done) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadLine = deadLine;
        this.createDate = createDate;
        this.done = done;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDateTime deadLine) {
        this.deadLine = deadLine;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }


    public Task(long id, String title, String description, Boolean done) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.done = done;
    }
}
