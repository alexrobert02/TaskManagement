package com.taskmanagement.service;

import com.taskmanagement.dto.ProjectCreationRequestDto;
import com.taskmanagement.dto.ProjectDto;
import com.taskmanagement.mapper.ProjectMapper;
import com.taskmanagement.model.Project;
import com.taskmanagement.model.User;
import com.taskmanagement.repository.ProjectRepository;
import com.taskmanagement.repository.TaskRepository;
import com.taskmanagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectMapper projectMapper;

    @InjectMocks
    private ProjectService projectService;

    private Project project;
    private ProjectDto projectDto;
    private ProjectCreationRequestDto projectCreationRequestDto;
    private List<User> users;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        project = new Project();
        project.setId(1L);
        project.setName("Test Project");
        project.setDescription("Project Description");

        projectDto = new ProjectDto();
        projectDto.setId(1L);
        projectDto.setName("Test Project");
        projectDto.setDescription("Project Description");

        projectCreationRequestDto = new ProjectCreationRequestDto();
        projectCreationRequestDto.setName("Test Project");
        projectCreationRequestDto.setDescription("Project Description");

        users = new ArrayList<>();
        users.add(new User(1L, "john.doe@example.com"));
        users.add(new User(2L, "jane.doe@example.com"));
    }

    @Test
    void getProjectById_shouldReturnProjectDto_whenProjectExists() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectMapper.toProjectDto(project)).thenReturn(projectDto);

        Optional<ProjectDto> result = projectService.getProjectById(1L);

        assertTrue(result.isPresent());
        assertEquals(projectDto, result.get());
        verify(projectRepository, times(1)).findById(1L);
        verify(projectMapper, times(1)).toProjectDto(project);
    }

    @Test
    void createProject_shouldReturnProjectDto_whenProjectIsCreated() {
        when(projectMapper.toProject(projectCreationRequestDto, userRepository, taskRepository)).thenReturn(project);
        when(projectRepository.save(project)).thenReturn(project);
        when(projectMapper.toProjectDto(project)).thenReturn(projectDto);

        ProjectDto result = projectService.createProject(projectCreationRequestDto);

        assertNotNull(result);
        assertEquals(projectDto, result);
        verify(projectRepository, times(1)).save(project);
        verify(projectMapper, times(1)).toProjectDto(project);
    }

    @Test
    void searchProjects_shouldReturnListOfProjectDtos_whenProjectsMatchCriteria() {
        List<Project> projects = List.of(project);
        List<ProjectDto> projectDtos = List.of(projectDto);

        when(projectRepository.searchProjects("Test Project", "Description")).thenReturn(projects);
        when(projectMapper.toProjectDto(project)).thenReturn(projectDto);

        List<ProjectDto> result = projectService.searchProjects("Test Project", "Description");

        assertNotNull(result);
        assertEquals(projectDtos.size(), result.size());
        assertEquals(projectDtos, result);
        verify(projectRepository, times(1)).searchProjects("Test Project", "Description");
        verify(projectMapper, times(1)).toProjectDto(project);
    }

    @Test
    void assignUsersToProject_shouldAddUsersToProject_whenProjectExists() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(userRepository.findAllById(List.of(1L, 2L))).thenReturn(users);

        projectService.assignUsersToProject(1L, List.of(1L, 2L));

        assertEquals(2, project.getUsers().size());
        verify(projectRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findAllById(List.of(1L, 2L));
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    void assignUsersToProject_shouldThrowException_whenProjectDoesNotExist() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                projectService.assignUsersToProject(1L, List.of(1L, 2L))
        );

        assertEquals("Project not found", exception.getMessage());
        verify(projectRepository, times(1)).findById(1L);
        verifyNoInteractions(userRepository);
    }

    @Test
    void deleteProject_shouldDeleteProject_whenProjectExists() {
        doNothing().when(projectRepository).deleteById(1L);

        projectService.deleteProject(1L);

        verify(projectRepository, times(1)).deleteById(1L);
    }
}
