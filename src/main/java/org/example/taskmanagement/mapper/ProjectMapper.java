package org.example.taskmanagement.mapper;

import org.example.taskmanagement.dto.ProjectCreationRequestDto;
import org.example.taskmanagement.dto.ProjectDto;
import org.example.taskmanagement.model.Project;
import org.example.taskmanagement.model.Task;
import org.example.taskmanagement.model.User;
import org.example.taskmanagement.repository.TaskRepository;
import org.example.taskmanagement.repository.UserRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = { UserMapper.class , TaskMapper.class })
public interface ProjectMapper {

    @Mapping(target = "users", source = "userIds", qualifiedByName = "mapUsers")
    Project toProject(ProjectCreationRequestDto projectCreationRequestDto,
                      @Context UserRepository userRepository,
                      @Context TaskRepository taskRepository);

    @Named("mapUsers")
    default List<User> mapUsers(List<Long> userIds, @Context UserRepository userRepository) {
        System.out.println("Mapping users with IDs: " + userIds);
        return userIds != null ?
                userIds.stream()
                        .map(userId -> {
                            System.out.println("Mapping user with ID: " + userId);
                            return userId != null ? userRepository.findById(userId).orElse(null) : null;
                        })
                        .toList()
                : null;    }

    ProjectDto toProjectDto(Project project);

    @Named("mapTaskIds")
    default List<Long> mapTaskIds(List<Task> tasks) {
        System.out.println("Mapping users to IDs");
        return tasks != null ?
                tasks.stream()
                        .map(task -> {
                            System.out.println("Mapping task to ID: " + (task != null ? task.getId() : null));
                            return task != null ? task.getId() : null;
                        })
                        .toList()
                : null;
    }
}
