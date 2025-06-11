package com.taskmanagement.dto;

import com.taskmanagement.model.Priority;
import com.taskmanagement.model.Status;
import lombok.Data;

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
