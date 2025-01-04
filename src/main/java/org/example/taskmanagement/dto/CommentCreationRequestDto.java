package org.example.taskmanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreationRequestDto {
    private String content;
    private Long authorId;
    private Long taskId;
}
