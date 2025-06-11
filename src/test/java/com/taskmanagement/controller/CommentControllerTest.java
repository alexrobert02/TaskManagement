package com.taskmanagement.controller;

import com.taskmanagement.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import com.taskmanagement.dto.CommentCreationRequestDto;
import com.taskmanagement.dto.CommentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentControllerTest {

    @InjectMocks
    private CommentController commentController;

    @Mock
    private CommentService commentService;

    private CommentCreationRequestDto commentCreationRequestDto;
    private CommentDto commentDto;

    @BeforeEach
    void setUp() {
        commentCreationRequestDto = new CommentCreationRequestDto();
        commentCreationRequestDto.setContent("Sample comment");

        commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setContent("Sample comment");
    }

    @Test
    void createComment_shouldReturnCreatedStatus() {
        when(commentService.createComment(any(CommentCreationRequestDto.class)))
                .thenReturn(commentDto);

        ResponseEntity<CommentDto> response = commentController.createComment(commentCreationRequestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(commentDto.getId(), response.getBody().getId());
        assertEquals(commentDto.getContent(), response.getBody().getContent());

        verify(commentService, times(1)).createComment(any(CommentCreationRequestDto.class));
    }

    @Test
    void updateCommentContent_shouldReturnOk_whenCommentExists() {
        commentDto.setContent("Updated comment content");

        when(commentService.updateContent(1L, "Updated comment content"))
                .thenReturn(commentDto);

        ResponseEntity<CommentDto> response = commentController.updateCommentContent(1L, "Updated comment content");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated comment content", response.getBody().getContent());

        verify(commentService, times(1)).updateContent(1L, "Updated comment content");
    }

    @Test
    void updateCommentContent_shouldReturnNotFound_whenCommentDoesNotExist() {
        when(commentService.updateContent(1L, "Updated comment content"))
                .thenThrow(new EntityNotFoundException());

        ResponseEntity<CommentDto> response = commentController.updateCommentContent(1L, "Updated comment content");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(commentService, times(1)).updateContent(1L, "Updated comment content");
    }

    @Test
    void getAllComments_shouldReturnOk_whenCommentsExist() {
        List<CommentDto> comments = new ArrayList<>();
        comments.add(commentDto);

        when(commentService.getAllComments()).thenReturn(comments);

        ResponseEntity<List<CommentDto>> response = commentController.getAllComments();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());

        verify(commentService, times(1)).getAllComments();
    }

    @Test
    void getAllComments_shouldReturnNotFound_whenNoCommentsExist() {
        when(commentService.getAllComments()).thenReturn(new ArrayList<>());

        ResponseEntity<List<CommentDto>> response = commentController.getAllComments();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(commentService, times(1)).getAllComments();
    }
}
