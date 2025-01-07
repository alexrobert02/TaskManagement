package org.example.taskmanagement.service;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.dto.ProjectCreationRequestDto;
import org.example.taskmanagement.dto.ProjectDto;
import org.example.taskmanagement.mapper.ProjectMapper;
import org.example.taskmanagement.model.Project;
import org.example.taskmanagement.model.User;
import org.example.taskmanagement.repository.ProjectRepository;
import org.example.taskmanagement.repository.TaskRepository;
import org.example.taskmanagement.repository.UserRepository;
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
