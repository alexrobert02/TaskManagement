package org.example.taskmanagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProjectDto {
    private Long id;
    private String name;
    private String description;
    private List<Long> userIds;
    private List<Long> taskIds;
}
