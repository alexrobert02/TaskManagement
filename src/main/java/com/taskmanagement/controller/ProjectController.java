package com.taskmanagement.controller;

import com.taskmanagement.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.taskmanagement.dto.ProjectCreationRequestDto;
import com.taskmanagement.dto.ProjectDto;
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

    @Operation(
            summary = "Retrieve project by ID",
            description = "Fetches a project based on the provided project ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Project found"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectById(
            @PathVariable @Parameter(description = "ID of the project to be retrieved") Long id) {
        Optional<ProjectDto> project = projectService.getProjectById(id);
        return project.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Search for projects",
            description = "Search projects based on name and/or description."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Projects found"),
            @ApiResponse(responseCode = "404", description = "No projects found")
    })
    @GetMapping("/search")
    public ResponseEntity<List<ProjectDto>> searchProjects(
            @RequestParam(required = false) @Parameter(description = "Project name") String name,
            @RequestParam(required = false) @Parameter(description = "Project description") String description) {
        List<ProjectDto> projects = projectService.searchProjects(name, description);
        if (projects.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(projects);
    }

    @Operation(
            summary = "Create a new project",
            description = "Creates a new project based on the provided details."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Project created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<ProjectDto> createProject(
            @Valid @RequestBody @Parameter(description = "Details of the project to be created")
            ProjectCreationRequestDto projectCreationRequestDto) {
        ProjectDto project = projectService.createProject(projectCreationRequestDto);
        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Assign users to a project",
            description = "Assigns a list of users to a project."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users assigned successfully"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    @PatchMapping("/{projectId}/assign-users")
    public ResponseEntity<Void> assignUsersToProject(
            @PathVariable @Parameter(description = "ID of the project to assign users to") Long projectId,
            @RequestBody @Parameter(description = "List of user IDs to be assigned to the project") List<Long> userIds) {
        try {
            projectService.assignUsersToProject(projectId, userIds);
            return ResponseEntity.ok().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }


    @Operation(
            summary = "Delete a project",
            description = "Deletes a project based on the provided project ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Project deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(
            @PathVariable @Parameter(description = "ID of the project to be deleted") Long id) {
        try {
            projectService.deleteProject(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
