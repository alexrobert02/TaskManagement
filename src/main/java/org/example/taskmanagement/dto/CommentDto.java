package org.example.taskmanagement.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Long id;
    private String content;
    private Long authorId;
    private Long taskId;
    private LocalDateTime createdAt;
}
