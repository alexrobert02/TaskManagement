package org.example.taskmanagement.dto;

import lombok.Data;
import org.example.taskmanagement.model.Priority;
import org.example.taskmanagement.model.Status;

import java.time.LocalDate;
import java.util.List;

@Data
public class TaskDto {
    private Long id;
    private String name;
    private String details;
    private Status status;
    private Priority priority;
    private LocalDate dueDate;
    private List<CommentDto> comments;
    private Long projectId;
    private UserDto assignedUser;
}
