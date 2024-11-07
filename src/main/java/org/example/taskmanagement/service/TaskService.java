package org.example.taskmanagement.service;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.model.Task;
import org.example.taskmanagement.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    private final TaskHistoryService taskHistoryService;

    public Task createTask(Task task) {
        Task newTask = taskRepository.save(task);
        taskHistoryService.createInitialHistory(newTask);
        return newTask;
    }

    public Optional<Task> getTask(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task updateTask(Long taskId, Task updatedTask, String updatedBy) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));

        String previousStatus = task.getStatus();
        task.setName(updatedTask.getName());
        task.setDescription(updatedTask.getDescription());
        task.setPriority(updatedTask.getPriority());
        task.setStatus(updatedTask.getStatus());
        task.setDeadline(updatedTask.getDeadline());

        taskRepository.save(task);
        taskHistoryService.logStatusChange(task, previousStatus, task.getStatus(), updatedBy);

        return task;
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
