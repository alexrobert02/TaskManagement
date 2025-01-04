package org.example.taskmanagement.service;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.dto.TaskCreationRequestDto;
import org.example.taskmanagement.dto.TaskDto;
import org.example.taskmanagement.mapper.TaskMapper;
import org.example.taskmanagement.model.Project;
import org.example.taskmanagement.model.Task;
import org.example.taskmanagement.model.User;
import org.example.taskmanagement.repository.ProjectRepository;
import org.example.taskmanagement.repository.TaskRepository;
import org.example.taskmanagement.repository.UserRepository;
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

    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toTaskDto)
                .toList();
    }

    public Optional<TaskDto> getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(taskMapper::toTaskDto);
    }

//    public TaskDto createTask(TaskCreationRequestDto taskCreationRequestDto) {
//        Task task = taskMapper.toTask(taskCreationRequestDto, userRepository, projectRepository);
//        userRepository.findById(taskCreationRequestDto.getAssignedUserId())
//                .ifPresent(task::setAssignedUser);
//        projectRepository.findById(taskCreationRequestDto.getProjectId())
//                .ifPresent(task::setProject);
//        System.out.println("Before saving...");
//        Task savedTask = taskRepository.save(task);
//        System.out.println("Task created: " + savedTask);
//        System.out.println("Mapped task: " + taskMapper.toTaskDto(savedTask));
//        return taskMapper.toTaskDto(savedTask);
//    }

        public Task createTask(TaskCreationRequestDto taskCreationRequestDto) {
            Task task = taskMapper.toTask(taskCreationRequestDto, userRepository);
            userRepository.findById(taskCreationRequestDto.getAssignedUserId())
                    .ifPresent(task::setAssignedUser);
            Project project = projectRepository.findById(taskCreationRequestDto.getProjectId())
                    .orElseThrow(() -> new IllegalArgumentException("Project not found"));
            project.addTask(task);

            Task savedTask = taskRepository.save(task);
            return savedTask;
        }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
