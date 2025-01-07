package org.example.taskmanagement.controller;

import org.example.taskmanagement.dto.TaskCreationRequestDto;
import org.example.taskmanagement.dto.TaskDto;
import org.example.taskmanagement.model.Priority;
import org.example.taskmanagement.model.Status;
import org.example.taskmanagement.service.TaskService;
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
class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskService taskService;

    private TaskCreationRequestDto taskCreationRequestDto;
    private TaskDto taskDto;

    @BeforeEach
    void setUp() {
        taskCreationRequestDto = new TaskCreationRequestDto();
        taskCreationRequestDto.setName("Test Task");
        taskCreationRequestDto.setDetails("A test task for unit testing.");
        taskCreationRequestDto.setStatus(Status.NOT_STARTED);
        taskCreationRequestDto.setPriority(Priority.HIGH);

        taskDto = new TaskDto();
        taskDto.setId(1L);
        taskDto.setName("Test Task");
        taskDto.setDetails("A test task for unit testing.");
        taskDto.setStatus(Status.NOT_STARTED);
        taskDto.setPriority(Priority.HIGH);
    }

    @Test
    void createTask_shouldReturnCreated_whenTaskIsCreated() {
        when(taskService.createTask(any(TaskCreationRequestDto.class)))
                .thenReturn(taskDto);

        ResponseEntity<TaskDto> response = taskController.createTask(taskCreationRequestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(taskDto.getId(), response.getBody().getId());
        assertEquals(taskDto.getName(), response.getBody().getName());

        verify(taskService, times(1)).createTask(any(TaskCreationRequestDto.class));
    }

    @Test
    void searchTasks_shouldReturnOk_whenTasksFound() {
        List<TaskDto> tasks = new ArrayList<>();
        tasks.add(taskDto);

        when(taskService.searchTasks(any(Status.class), any(Priority.class), anyLong()))
                .thenReturn(tasks);

        ResponseEntity<List<TaskDto>> response = taskController.searchTasks(Status.NOT_STARTED, Priority.HIGH, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());

        verify(taskService, times(1)).searchTasks(any(Status.class), any(Priority.class), anyLong());
    }

    @Test
    void searchTasks_shouldReturnNotFound_whenNoTasksFound() {
        when(taskService.searchTasks(any(Status.class), any(Priority.class), anyLong()))
                .thenReturn(new ArrayList<>());

        ResponseEntity<List<TaskDto>> response = taskController.searchTasks(Status.NOT_STARTED, Priority.HIGH, 1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(taskService, times(1)).searchTasks(any(Status.class), any(Priority.class), anyLong());
    }

    @Test
    void getTasksWithUpcomingDueDates_shouldReturnOk_whenTasksFound() {
        List<TaskDto> tasks = new ArrayList<>();
        tasks.add(taskDto);

        when(taskService.getTasksWithUpcomingDueDates(1L)).thenReturn(tasks);

        ResponseEntity<List<TaskDto>> response = taskController.getTasksWithUpcomingDueDates(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());

        verify(taskService, times(1)).getTasksWithUpcomingDueDates(1L);
    }

    @Test
    void getTasksWithUpcomingDueDates_shouldReturnNotFound_whenNoTasksFound() {
        when(taskService.getTasksWithUpcomingDueDates(1L)).thenReturn(new ArrayList<>());

        ResponseEntity<List<TaskDto>> response = taskController.getTasksWithUpcomingDueDates(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(taskService, times(1)).getTasksWithUpcomingDueDates(1L);
    }

    @Test
    void getTaskById_shouldReturnOk_whenTaskExists() {
        when(taskService.getTaskById(1L)).thenReturn(Optional.of(taskDto));

        ResponseEntity<TaskDto> response = taskController.getTaskById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(taskDto.getId(), response.getBody().getId());

        verify(taskService, times(1)).getTaskById(1L);
    }

    @Test
    void getTaskById_shouldReturnNotFound_whenTaskDoesNotExist() {
        when(taskService.getTaskById(1L)).thenReturn(Optional.empty());

        ResponseEntity<TaskDto> response = taskController.getTaskById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(taskService, times(1)).getTaskById(1L);
    }

    @Test
    void deleteTask_shouldReturnNoContent_whenTaskDeletedSuccessfully() {
        doNothing().when(taskService).deleteTask(1L);

        ResponseEntity<Void> response = taskController.deleteTask(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(taskService, times(1)).deleteTask(1L);
    }

    @Test
    void deleteTask_shouldReturnNotFound_whenTaskDoesNotExist() {
        doThrow(new RuntimeException()).when(taskService).deleteTask(1L);

        ResponseEntity<Void> response = taskController.deleteTask(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(taskService, times(1)).deleteTask(1L);
    }

    @Test
    void updateTask_shouldReturnOk_whenTaskUpdated() {
        when(taskService.updateTask(eq(1L), any(TaskCreationRequestDto.class)))
                .thenReturn(taskDto);

        ResponseEntity<TaskDto> response = taskController.updateTask(1L, taskCreationRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(taskDto.getId(), response.getBody().getId());

        verify(taskService, times(1)).updateTask(eq(1L), any(TaskCreationRequestDto.class));
    }


    @Test
    void updateTask_shouldReturnNotFound_whenTaskDoesNotExist() {
        when(taskService.updateTask(eq(1L), any(TaskCreationRequestDto.class)))
                .thenThrow(new RuntimeException());

        ResponseEntity<TaskDto> response = taskController.updateTask(1L, taskCreationRequestDto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(taskService, times(1)).updateTask(eq(1L), any(TaskCreationRequestDto.class));
    }
}
