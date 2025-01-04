package org.example.taskmanagement.mapper;

import org.example.taskmanagement.dto.ProjectCreationRequestDto;
import org.example.taskmanagement.dto.ProjectDto;
import org.example.taskmanagement.model.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    Project toProject(ProjectCreationRequestDto projectCreationRequestDto);
    ProjectDto toProjectDto(Project project);
}
