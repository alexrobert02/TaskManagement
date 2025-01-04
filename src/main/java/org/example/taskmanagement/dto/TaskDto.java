package org.example.taskmanagement.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TaskDto {
    private Long id;
    private String name;
    private String description;
    private String status;
    private String priority;
    private LocalDateTime deadline;
    private List<CommentDto> comments;
    private Long projectId;
    private Long assignedUserId;
}
