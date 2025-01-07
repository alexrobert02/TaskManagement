package org.example.taskmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.taskmanagement.model.Priority;
import org.example.taskmanagement.model.Status;
import org.example.taskmanagement.validation.ValidDueDate;

import java.time.LocalDate;

@Data
public class TaskCreationRequestDto {

    @NotBlank(message = "Task name is required")
    @Size(max = 100, message = "Task name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Details are required")
    @Size(max = 500, message = "Details must not exceed 500 characters")
    private String details;

    @NotNull(message = "Status is required")
    private Status status;

    @NotNull(message = "Priority is required")
    private Priority priority;

    @NotNull(message = "Due date  is required")
    @ValidDueDate
    private LocalDate dueDate;

    @NotNull(message = "Project ID is required")
    private Long projectId;

    @NotNull(message = "Assigned user ID is required")
    private Long assignedUserId;
}
