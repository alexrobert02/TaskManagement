package org.example.taskmanagement.dto;

import lombok.Data;

@Data
public class TaskCreationRequestDto {
    private String name;
    private String description;
    private Long projectId;
    private Long assignedUserId;
}
