package com.taskmanagement.dto;

import com.taskmanagement.model.Priority;
import com.taskmanagement.model.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import com.taskmanagement.validation.ValidDueDate;

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
