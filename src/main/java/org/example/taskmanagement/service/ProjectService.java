package org.example.taskmanagement.service;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.dto.ProjectCreationRequestDto;
import org.example.taskmanagement.dto.ProjectDto;
import org.example.taskmanagement.mapper.ProjectMapper;
import org.example.taskmanagement.model.Project;
import org.example.taskmanagement.model.User;
import org.example.taskmanagement.repository.ProjectRepository;
import org.example.taskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final UserRepository userRepository;

    private final ProjectMapper projectMapper;

    public List<Project> getAllProjects() {
        return projectRepository.findAll().stream()
                //.map(projectMapper::toProjectDto)
                .toList();
    }

    public Optional<ProjectDto> getProjectById(Long id) {
        return projectRepository.findById(id)
                .map(projectMapper::toProjectDto);
    }

    public Project createProject(ProjectCreationRequestDto projectCreationRequestDto) {
        Project project = projectMapper.toProject(projectCreationRequestDto);
        List<User> users = userRepository.findAllById(projectCreationRequestDto.getUserIds());
        project.setUsers(users);
        //return projectMapper.toProjectDto(projectRepository.save(project));
        return projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
