package org.example.domain;
import jakarta.persistence.*;




@Entity
@Table(schema= "todo",name = "task")

public class Task {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
private Integer id;
    private String description;
    @Enumerated (EnumType.ORDINAL)
    private Status status;

    public Task(Integer id, String description, Status status) {
        this.id = id;
        this.description = description;
        this.status = status;
    }

    public Task() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
