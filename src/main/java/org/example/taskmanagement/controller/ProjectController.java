package org.example.taskmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.dto.ProjectCreationRequestDto;
import org.example.taskmanagement.dto.ProjectDto;
import org.example.taskmanagement.model.Project;
import org.example.taskmanagement.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    // Get all projects
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    // Get project by id
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long id) {
        Optional<ProjectDto> project = projectService.getProjectById(id);
        return project.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create new project
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody ProjectCreationRequestDto projectCreationRequestDto) {
        Project project = projectService.createProject(projectCreationRequestDto);
        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }

    // Delete project by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
