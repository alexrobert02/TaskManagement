package com.taskmanagement.service;

import com.taskmanagement.mapper.TaskMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import com.taskmanagement.dto.TaskCreationRequestDto;
import com.taskmanagement.dto.TaskDto;
import com.taskmanagement.model.Priority;
import com.taskmanagement.model.Status;
import com.taskmanagement.model.Task;
import com.taskmanagement.repository.ProjectRepository;
import com.taskmanagement.repository.TaskRepository;
import com.taskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    private final UserRepository userRepository;

    private final ProjectRepository projectRepository;

    public Optional<TaskDto> getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(taskMapper::toTaskDto);
    }

    public TaskDto createTask(TaskCreationRequestDto taskCreationRequestDto) {
        Task task = taskMapper.toTask(taskCreationRequestDto, userRepository, projectRepository);
        return taskMapper.toTaskDto(taskRepository.save(task));
    }

    public List<TaskDto> searchTasks(Status status, Priority priority, Long assignedUserId){
        return taskRepository.searchTasks(status, priority, assignedUserId).stream()
                .map(taskMapper::toTaskDto)
                .toList();
    }

    public List<TaskDto> getTasksWithUpcomingDueDates(Long userId) {
        return taskRepository.findTasksWithUpcomingDueDates(userId).stream()
                .map(taskMapper::toTaskDto)
                .toList();
    }

    public TaskDto updateTask(Long id, TaskCreationRequestDto taskCreationRequestDto) {
        return taskRepository.findById(id)
                .map(task -> {
                    Task updatedTask = taskMapper.updateTaskFromDto(taskCreationRequestDto, task, userRepository, projectRepository);
                    return taskMapper.toTaskDto(taskRepository.save(updatedTask));
                }).orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }


}
