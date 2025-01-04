package org.example.taskmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.dto.TaskCreationRequestDto;
import org.example.taskmanagement.dto.TaskDto;
import org.example.taskmanagement.model.Task;
import org.example.taskmanagement.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskCreationRequestDto taskCreationRequestDto) {
        Task taskDto = taskService.createTask(taskCreationRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskDto);
    }

    // Get all tasks
    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        List<TaskDto> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    // Get task by id
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        Optional<TaskDto> task = taskService.getTaskById(id);
        return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

//    @PutMapping("/{id}")
//    public Task updateTask(@PathVariable Long id, @RequestBody Task task, @RequestParam String updatedBy) {
//        return taskService.updateTask(id, task, updatedBy);
//    }

    // Delete task by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
