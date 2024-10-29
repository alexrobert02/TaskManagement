package org.example.taskmanagement.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class TaskHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String actionType;
    private String performedBy;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public TaskHistory(String actionType, String performedBy, Task task) {
        this.actionType = actionType;
        this.performedBy = performedBy;
        this.task = task;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
}

