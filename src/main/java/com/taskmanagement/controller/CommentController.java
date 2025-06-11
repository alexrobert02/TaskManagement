package com.taskmanagement.controller;

import com.taskmanagement.dto.CommentCreationRequestDto;
import com.taskmanagement.dto.CommentDto;
import com.taskmanagement.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(
            summary = "Create a new comment",
            description = "Creates a new comment and returns the created comment details."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Comment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentCreationRequestDto commentCreationRequestDto) {
        CommentDto taskDto = commentService.createComment(commentCreationRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskDto);
    }

    @Operation(
            summary = "Update the content of a comment",
            description = "Updates the content of a comment based on its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comment updated successfully"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateCommentContent(@PathVariable Long id, @RequestBody String content) {
        try {
            CommentDto updatedComment = commentService.updateContent(id, content);
            return ResponseEntity.ok(updatedComment);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Retrieve all comments",
            description = "Fetches all the comments in the system."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No comments found")
    })
    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments() {
        List<CommentDto> comments = commentService.getAllComments();
        if (comments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comments);
    }
}
