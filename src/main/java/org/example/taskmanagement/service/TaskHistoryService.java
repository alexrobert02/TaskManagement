package org.example.taskmanagement.service;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.model.Task;
import org.example.taskmanagement.model.TaskHistory;
import org.example.taskmanagement.repository.TaskHistoryRepository;
import org.example.taskmanagement.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskHistoryService {

    private TaskHistoryRepository taskHistoryRepository;

    private TaskRepository taskRepository;

    public void logTaskAction(Long taskId, String actionType, String performedBy) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
        TaskHistory history = new TaskHistory(actionType, performedBy, task);
        taskHistoryRepository.save(history);
    }

    public List<TaskHistory> getTaskHistory(Long taskId) {
        return taskHistoryRepository.findByTaskId(taskId);
    }
}
