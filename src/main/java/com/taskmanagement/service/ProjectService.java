package com.taskmanagement.service;

import lombok.RequiredArgsConstructor;
import com.taskmanagement.dto.ProjectCreationRequestDto;
import com.taskmanagement.dto.ProjectDto;
import com.taskmanagement.mapper.ProjectMapper;
import com.taskmanagement.model.Project;
import com.taskmanagement.model.User;
import com.taskmanagement.repository.ProjectRepository;
import com.taskmanagement.repository.TaskRepository;
import com.taskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final UserRepository userRepository;

    private final TaskRepository taskRepository;

    private final ProjectMapper projectMapper;

    public Optional<ProjectDto> getProjectById(Long id) {
        return projectRepository.findById(id)
                .map(projectMapper::toProjectDto);
    }

    public ProjectDto createProject(ProjectCreationRequestDto projectCreationRequestDto) {
        Project project = projectMapper.toProject(projectCreationRequestDto, userRepository, taskRepository);
        return projectMapper.toProjectDto(projectRepository.save(project));
    }

    public List<ProjectDto> searchProjects(String name, String description) {
        return projectRepository.searchProjects(name, description).stream()
                .map(projectMapper::toProjectDto)
                .toList();
    }

    public void assignUsersToProject(Long projectId, List<Long> userIds) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        List<User> users = userRepository.findAllById(userIds);
        project.getUsers().addAll(users);
        projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
