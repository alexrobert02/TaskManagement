package org.example.taskmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.model.TaskHistory;
import org.example.taskmanagement.service.TaskHistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskHistoryController {

    private final TaskHistoryService taskHistoryService;

    @PostMapping("/{taskId}/log")
    public void logTaskAction(
            @PathVariable Long taskId,
            @RequestParam String actionType,
            @RequestParam String performedBy) {
        taskHistoryService.logTaskAction(taskId, actionType, performedBy);
    }

    @GetMapping("/{taskId}/history")
    public List<TaskHistory> getTaskHistory(@PathVariable Long taskId) {
        return taskHistoryService.getTaskHistory(taskId);
    }
}
