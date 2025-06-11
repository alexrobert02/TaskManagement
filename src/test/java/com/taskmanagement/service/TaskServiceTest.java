package com.taskmanagement.service;

import com.taskmanagement.dto.TaskCreationRequestDto;
import com.taskmanagement.dto.TaskDto;
import com.taskmanagement.dto.UserDto;
import com.taskmanagement.mapper.TaskMapper;
import com.taskmanagement.model.*;
import com.taskmanagement.repository.ProjectRepository;
import com.taskmanagement.repository.TaskRepository;
import com.taskmanagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;
    private TaskDto taskDto;
    private TaskCreationRequestDto taskCreationRequestDto;

    @BeforeEach
    void setUp() {
        User user = new User();
        Project project = new Project();
        List<Comment> comments = new ArrayList<>();

        UserDto userDto = new UserDto();
        task = new Task(1L, "Task Name", "Task Details", Status.NOT_STARTED, Priority.HIGH, LocalDate.now(), comments, user, project);

        taskDto = new TaskDto();
        taskDto.setId(1L);
        taskDto.setName("Task Name");
        taskDto.setDetails("Task Details");
        taskDto.setStatus(Status.NOT_STARTED);
        taskDto.setPriority(Priority.HIGH);
        taskDto.setDueDate(LocalDate.now());
        taskDto.setAssignedUser(userDto);
        taskDto.setProjectId(1L);

        taskCreationRequestDto = new TaskCreationRequestDto();
        taskCreationRequestDto.setName("Task Name");
        taskCreationRequestDto.setDetails("Task Details");
        taskCreationRequestDto.setStatus(Status.NOT_STARTED);
        taskCreationRequestDto.setPriority(Priority.HIGH);
        taskCreationRequestDto.setDueDate(LocalDate.now());
        taskCreationRequestDto.setAssignedUserId(1L);
        taskCreationRequestDto.setProjectId(1L);
    }

    @Test
    void getTaskById_shouldReturnTaskDto_whenTaskExists() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskMapper.toTaskDto(task)).thenReturn(taskDto);

        Optional<TaskDto> result = taskService.getTaskById(1L);

        assertTrue(result.isPresent());
        assertEquals(taskDto.getId(), result.get().getId());
        assertEquals(taskDto.getName(), result.get().getName());

        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void getTaskById_shouldReturnEmptyOptional_whenTaskDoesNotExist() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<TaskDto> result = taskService.getTaskById(1L);

        assertFalse(result.isPresent());

        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void createTask_shouldReturnTaskDto_whenTaskIsCreated() {
        when(taskMapper.toTask(taskCreationRequestDto, userRepository, projectRepository)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toTaskDto(task)).thenReturn(taskDto);

        TaskDto result = taskService.createTask(taskCreationRequestDto);

        assertNotNull(result);
        assertEquals(taskDto.getId(), result.getId());
        assertEquals(taskDto.getName(), result.getName());

        verify(taskMapper, times(1)).toTask(taskCreationRequestDto, userRepository, projectRepository);
        verify(taskRepository, times(1)).save(task);
        verify(taskMapper, times(1)).toTaskDto(task);
    }

    @Test
    void searchTasks_shouldReturnTasks_whenMatchingCriteria() {
        List<Task> tasks = Arrays.asList(task);

        when(taskRepository.searchTasks(Status.NOT_STARTED, Priority.HIGH, 1L)).thenReturn(tasks);
        when(taskMapper.toTaskDto(task)).thenReturn(taskDto);

        List<TaskDto> result = taskService.searchTasks(Status.NOT_STARTED, Priority.HIGH, 1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(tasks.size(), result.size());

        verify(taskRepository, times(1)).searchTasks(Status.NOT_STARTED, Priority.HIGH, 1L);
        verify(taskMapper, times(1)).toTaskDto(task);
    }

    @Test
    void getTasksWithUpcomingDueDates_shouldReturnTasks_whenTasksExist() {
        List<Task> tasks = Arrays.asList(task);

        when(taskRepository.findTasksWithUpcomingDueDates(1L)).thenReturn(tasks);
        when(taskMapper.toTaskDto(task)).thenReturn(taskDto);

        List<TaskDto> result = taskService.getTasksWithUpcomingDueDates(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(tasks.size(), result.size());

        verify(taskRepository, times(1)).findTasksWithUpcomingDueDates(1L);
        verify(taskMapper, times(1)).toTaskDto(task);
    }

    @Test
    void updateTask_shouldReturnUpdatedTaskDto_whenTaskExists() {
        Task updatedTask = new Task(1L, "Updated Task Name", "Updated Task Details", Status.NOT_STARTED, Priority.HIGH, LocalDate.now(), Collections.emptyList(), new User(), new Project());
        TaskDto updatedTaskDto = new TaskDto();
        UserDto updatedUserDto = new UserDto();
        updatedTaskDto.setId(1L);
        updatedTaskDto.setName("Updated Task Name");
        updatedTaskDto.setDetails("Updated Task Details");
        updatedTaskDto.setStatus(Status.NOT_STARTED);
        updatedTaskDto.setPriority(Priority.HIGH);
        updatedTaskDto.setDueDate(LocalDate.now());
        updatedTaskDto.setProjectId(1L);
        updatedTaskDto.setAssignedUser(updatedUserDto);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskMapper.updateTaskFromDto(taskCreationRequestDto, task, userRepository, projectRepository)).thenReturn(updatedTask);
        when(taskRepository.save(updatedTask)).thenReturn(updatedTask);
        when(taskMapper.toTaskDto(updatedTask)).thenReturn(updatedTaskDto);

        TaskDto result = taskService.updateTask(1L, taskCreationRequestDto);

        assertNotNull(result);
        assertEquals(updatedTaskDto.getId(), result.getId());
        assertEquals(updatedTaskDto.getName(), result.getName());

        verify(taskRepository, times(1)).findById(1L);
        verify(taskMapper, times(1)).updateTaskFromDto(taskCreationRequestDto, task, userRepository, projectRepository);
        verify(taskRepository, times(1)).save(updatedTask);
        verify(taskMapper, times(1)).toTaskDto(updatedTask);
    }

    @Test
    void updateTask_shouldThrowException_whenTaskDoesNotExist() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.updateTask(1L, taskCreationRequestDto));

        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void deleteTask_shouldDeleteTask_whenTaskExists() {
        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteTask_shouldNotDeleteTask_whenTaskDoesNotExist() {
        doThrow(new IllegalArgumentException("Task not found")).when(taskRepository).deleteById(1L);

        assertThrows(IllegalArgumentException.class, () -> taskService.deleteTask(1L));

        verify(taskRepository, times(1)).deleteById(1L);
    }
}
