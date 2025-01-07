package org.example.taskmanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.dto.TaskCreationRequestDto;
import org.example.taskmanagement.dto.TaskDto;
import org.example.taskmanagement.model.Priority;
import org.example.taskmanagement.model.Status;
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

    @Operation(
            summary = "Create a new task",
            description = "Creates a new task based on the provided task details."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Task created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<TaskDto> createTask(
            @Valid @RequestBody @Parameter(description = "Details of the task to be created")
            TaskCreationRequestDto taskCreationRequestDto) {
        TaskDto taskDto = taskService.createTask(taskCreationRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskDto);
    }

    @Operation(
            summary = "Search tasks",
            description = "Search tasks based on various filters such as status, priority, and assigned user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tasks found"),
            @ApiResponse(responseCode = "404", description = "No tasks found")
    })
    @GetMapping("/search")
    public ResponseEntity<List<TaskDto>> searchTasks(
            @RequestParam(required = false) @Parameter(description = "Filter by task status") Status status,
            @RequestParam(required = false) @Parameter(description = "Filter by task priority") Priority priority,
            @RequestParam(required = false) @Parameter(description = "Filter by assigned user ID") Long assignedUserId) {
        List<TaskDto> tasks = taskService.searchTasks(status, priority, assignedUserId);
        if (tasks.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tasks);
    }

    @Operation(
            summary = "Get tasks with upcoming due dates",
            description = "Fetches tasks that have upcoming due dates for a specified user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No tasks found for the user")
    })
    @GetMapping("/due-dates/upcoming/{userId}")
    public ResponseEntity<List<TaskDto>> getTasksWithUpcomingDueDates(
            @PathVariable @Parameter(description = "ID of the user to filter tasks by") Long userId) {
        List<TaskDto> tasks = taskService.getTasksWithUpcomingDueDates(userId);
        if (tasks.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tasks);
    }

    @Operation(
            summary = "Retrieve task by ID",
            description = "Fetches a task by its unique ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task found"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(
            @PathVariable @Parameter(description = "ID of the task to be retrieved") Long id) {
        Optional<TaskDto> task = taskService.getTaskById(id);

        return task.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @Operation(
            summary = "Delete task",
            description = "Deletes a task by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable @Parameter(description = "ID of the task to be deleted") Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }


    @Operation(
            summary = "Update task details",
            description = "Updates the details of an existing task based on the provided task ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task updated successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(
            @PathVariable @Parameter(description = "ID of the task to be updated") Long id,
            @RequestBody @Parameter(description = "Updated task details") TaskCreationRequestDto taskCreationRequestDto) {
        try {
            TaskDto updatedTask = taskService.updateTask(id, taskCreationRequestDto);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

}
