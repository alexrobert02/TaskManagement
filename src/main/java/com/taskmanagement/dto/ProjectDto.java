package com.taskmanagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProjectDto {
    private Long id;
    private String name;
    private String description;
    private List<UserDto> users;
    private List<TaskDto> tasks;
}
