package com.taskmanagement.controller;

import com.taskmanagement.service.ProjectService;
import com.taskmanagement.dto.ProjectCreationRequestDto;
import com.taskmanagement.dto.ProjectDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

    @InjectMocks
    private ProjectController projectController;

    @Mock
    private ProjectService projectService;

    private ProjectCreationRequestDto projectCreationRequestDto;
    private ProjectDto projectDto;

    @BeforeEach
    void setUp() {
        projectCreationRequestDto = new ProjectCreationRequestDto();
        projectCreationRequestDto.setName("Test Project");
        projectCreationRequestDto.setDescription("A test project for unit testing.");

        projectDto = new ProjectDto();
        projectDto.setId(1L);
        projectDto.setName("Test Project");
        projectDto.setDescription("A test project for unit testing.");
    }

    @Test
    void getProjectById_shouldReturnOk_whenProjectExists() {
        when(projectService.getProjectById(1L)).thenReturn(Optional.of(projectDto));

        ResponseEntity<ProjectDto> response = projectController.getProjectById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(projectDto.getId(), response.getBody().getId());
        assertEquals(projectDto.getName(), response.getBody().getName());
        assertEquals(projectDto.getDescription(), response.getBody().getDescription());

        verify(projectService, times(1)).getProjectById(1L);
    }

    @Test
    void getProjectById_shouldReturnNotFound_whenProjectDoesNotExist() {
        when(projectService.getProjectById(1L)).thenReturn(Optional.empty());

        ResponseEntity<ProjectDto> response = projectController.getProjectById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(projectService, times(1)).getProjectById(1L);
    }

    @Test
    void searchProjects_shouldReturnOk_whenProjectsExist() {
        List<ProjectDto> projects = new ArrayList<>();
        projects.add(projectDto);

        when(projectService.searchProjects("Test Project", null)).thenReturn(projects);

        ResponseEntity<List<ProjectDto>> response = projectController.searchProjects("Test Project", null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());

        verify(projectService, times(1)).searchProjects("Test Project", null);
    }

    @Test
    void searchProjects_shouldReturnNotFound_whenNoProjectsFound() {
        when(projectService.searchProjects("Nonexistent Project", null)).thenReturn(new ArrayList<>());

        ResponseEntity<List<ProjectDto>> response = projectController.searchProjects("Nonexistent Project", null);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(projectService, times(1)).searchProjects("Nonexistent Project", null);
    }

    @Test
    void createProject_shouldReturnCreatedStatus() {
        when(projectService.createProject(any(ProjectCreationRequestDto.class)))
                .thenReturn(projectDto);

        ResponseEntity<ProjectDto> response = projectController.createProject(projectCreationRequestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(projectDto.getId(), response.getBody().getId());
        assertEquals(projectDto.getName(), response.getBody().getName());
        assertEquals(projectDto.getDescription(), response.getBody().getDescription());

        verify(projectService, times(1)).createProject(any(ProjectCreationRequestDto.class));
    }

    @Test
    void assignUsersToProject_shouldReturnOk_whenUsersAssignedSuccessfully() {
        List<Long> userIds = new ArrayList<>();
        userIds.add(1L);

        doNothing().when(projectService).assignUsersToProject(1L, userIds);

        ResponseEntity<Void> response = projectController.assignUsersToProject(1L, userIds);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(projectService, times(1)).assignUsersToProject(1L, userIds);
    }

    @Test
    void assignUsersToProject_shouldReturnNotFound_whenProjectDoesNotExist() {
        List<Long> userIds = new ArrayList<>();
        userIds.add(1L);

        doThrow(new RuntimeException()).when(projectService).assignUsersToProject(1L, userIds);

        ResponseEntity<Void> response = projectController.assignUsersToProject(1L, userIds);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(projectService, times(1)).assignUsersToProject(1L, userIds);
    }

    @Test
    void deleteProject_shouldReturnNoContent_whenProjectDeletedSuccessfully() {
        doNothing().when(projectService).deleteProject(1L);

        ResponseEntity<Void> response = projectController.deleteProject(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(projectService, times(1)).deleteProject(1L);
    }

    @Test
    void deleteProject_shouldReturnNotFound_whenProjectDoesNotExist() {
        doThrow(new RuntimeException()).when(projectService).deleteProject(1L);

        ResponseEntity<Void> response = projectController.deleteProject(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(projectService, times(1)).deleteProject(1L);
    }
}
