package org.example.taskmanagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@NotBlank(message = "Task name is required")
    //@Size(max = 100, message = "Task name must not exceed 100 characters")
    private String name;

    //@NotBlank(message = "Description is required")
    //@Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    //@NotBlank(message = "Status is required")
    //@Size(max = 20, message = "Status must not exceed 20 characters")
    private String status;

    //@NotBlank(message = "Priority is required")
    //@Size(max = 20, message = "Priority must not exceed 20 characters")
    private String priority;

    //@NotBlank(message = "Deadline is required")
    private LocalDateTime deadline;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "task_id")
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne//(fetch = FetchType.LAZY) // Lazy loading to improve performance
    @JoinColumn(name = "user_id", nullable = false) // Foreign key column
    private User assignedUser;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "project_id", nullable = false)
//    @JsonBackReference
//    private Project project;
}
