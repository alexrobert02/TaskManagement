package org.example.taskmanagement.mapper;

import org.example.taskmanagement.dto.TaskCreationRequestDto;
import org.example.taskmanagement.dto.TaskDto;
import org.example.taskmanagement.model.Project;
import org.example.taskmanagement.model.Task;
import org.example.taskmanagement.model.User;
import org.example.taskmanagement.repository.ProjectRepository;
import org.example.taskmanagement.repository.UserRepository;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = CommentMapper.class)
public interface TaskMapper {

    @Mapping(target = "projectId", source = "project.id")
    TaskDto toTaskDto(Task task);

    @Mapping(target = "assignedUser", source = "assignedUserId", qualifiedByName = "mapAssignedUser")
    @Mapping(target = "project", source = "projectId", qualifiedByName = "mapProject")
    Task toTask(TaskCreationRequestDto taskCreationRequestDto,
                @Context UserRepository userRepository,
                @Context ProjectRepository projectRepository);

    @Mapping(target = "assignedUser", source = "assignedUserId", qualifiedByName = "mapAssignedUser")
    @Mapping(target = "project", source = "projectId", qualifiedByName = "mapProject")
    Task updateTaskFromDto(TaskCreationRequestDto dto,
                           @MappingTarget Task task,
                           @Context UserRepository userRepository,
                           @Context ProjectRepository projectRepository);

    @Named("mapAssignedUser")
    default User mapAssignedUser(Long assignedUserId, @Context UserRepository userRepository) {
        System.out.println("Mapping user with ID: " + assignedUserId);
        return assignedUserId != null ? userRepository.findById(assignedUserId).orElse(null) : null;
    }

    @Named("mapProject")
    default Project mapProject(Long projectId, @Context ProjectRepository projectRepository) {
        System.out.println("Mapping project with ID: " + projectId);
        return projectId != null ? projectRepository.findById(projectId).orElse(null) : null;
    }
}