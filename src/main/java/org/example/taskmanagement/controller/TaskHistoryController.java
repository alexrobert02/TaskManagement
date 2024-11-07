package org.example.taskmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.model.TaskHistory;
import org.example.taskmanagement.service.TaskHistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task-history")
@RequiredArgsConstructor
public class TaskHistoryController {
    
    private final TaskHistoryService taskHistoryService;

    @GetMapping("/task/{taskId}")
    public List<TaskHistory> getTaskHistoryByTaskId(@PathVariable Long taskId) {
        return taskHistoryService.getHistoryByTaskId(taskId);
    }

    @GetMapping
    public List<TaskHistory> getAllTaskHistories() {
        return taskHistoryService.getAllTaskHistories();
    }
}
