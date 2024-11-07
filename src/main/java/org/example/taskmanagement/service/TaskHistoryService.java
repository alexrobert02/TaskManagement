package org.example.taskmanagement.service;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.model.Task;
import org.example.taskmanagement.model.TaskHistory;
import org.example.taskmanagement.repository.TaskHistoryRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskHistoryService {

    private TaskHistoryRepository taskHistoryRepository;

    public void createInitialHistory(Task task) {
        TaskHistory history = new TaskHistory();
        history.setTask(task);
        history.setChangeDate(LocalDateTime.now());
        history.setPreviousStatus(null);
        history.setNewStatus(task.getStatus());
        history.setUpdatedBy("System");
        taskHistoryRepository.save(history);
    }

    public void logStatusChange(Task task, String previousStatus, String newStatus, String updatedBy) {
        TaskHistory history = new TaskHistory();
        history.setTask(task);
        history.setChangeDate(LocalDateTime.now());
        history.setPreviousStatus(previousStatus);
        history.setNewStatus(newStatus);
        history.setUpdatedBy(updatedBy);
        taskHistoryRepository.save(history);
    }

    public List<TaskHistory> getHistoryByTaskId(Long taskId) {
        return taskHistoryRepository.findByTaskId(taskId);
    }

    public List<TaskHistory> getAllTaskHistories() {
        return taskHistoryRepository.findAll();
    }
}
