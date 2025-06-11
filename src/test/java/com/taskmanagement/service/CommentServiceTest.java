package com.taskmanagement.service;

import com.taskmanagement.dto.CommentCreationRequestDto;
import com.taskmanagement.dto.CommentDto;
import com.taskmanagement.mapper.CommentMapper;
import com.taskmanagement.model.*;
import com.taskmanagement.repository.TaskRepository;
import com.taskmanagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import com.taskmanagement.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    private CommentService commentService;

    @Mock private CommentRepository commentRepository;
    @Mock private TaskRepository taskRepository;
    @Mock private UserRepository userRepository;
    @Mock private CommentMapper commentMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        commentService = new CommentService(taskRepository, userRepository, commentMapper, commentRepository);

        commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setContent("This is a comment");
        commentDto.setAuthorId(1L);
        commentDto.setTaskId(1L);
        commentDto.setCreatedAt(LocalDateTime.now());
    }

    private final Task task = new Task(1L, "Task 1", "Task description", Status.IN_PROGRESS, Priority.HIGH, LocalDate.now(), new ArrayList<>(), new User(), new Project());
    private final User user = new User(1L, "user@example.com");
    private final CommentCreationRequestDto commentCreationRequestDto = new CommentCreationRequestDto("This is a comment", 1L, 1L);
    private CommentDto commentDto;

    @Test
    void createComment_shouldReturnCommentDto_whenCommentIsCreated() {
        Comment comment = new Comment(1L, "This is a comment", LocalDateTime.now(), task, user);

        when(commentMapper.toComment(commentCreationRequestDto, userRepository, taskRepository)).thenReturn(comment);
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(commentMapper.toCommentDto(comment)).thenReturn(commentDto);

        CommentDto result = commentService.createComment(commentCreationRequestDto);

        assertNotNull(result);
        assertEquals(commentDto.getId(), result.getId());
        assertEquals(commentDto.getContent(), result.getContent());
        assertEquals(commentDto.getAuthorId(), result.getAuthorId());
        assertEquals(commentDto.getTaskId(), result.getTaskId());

        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void getAllComments_shouldReturnListOfComments_whenCommentsExist() {
        Comment comment = new Comment(1L, "This is a comment", LocalDateTime.now(), task, user);

        when(commentRepository.findAll()).thenReturn(List.of(comment));
        when(commentMapper.toCommentDto(any(Comment.class))).thenReturn(commentDto);

        List<CommentDto> result = commentService.getAllComments();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(commentDto.getContent(), result.get(0).getContent());
        assertEquals(commentDto.getAuthorId(), result.get(0).getAuthorId());
        assertEquals(commentDto.getTaskId(), result.get(0).getTaskId());

        verify(commentRepository, times(1)).findAll();
    }

    @Test
    void updateContent_shouldReturnUpdatedCommentDto_whenCommentExists() {
        Comment comment = new Comment(1L, "This is a comment", LocalDateTime.now(), task, user);
        String updatedContent = "Updated comment content";

        CommentDto updatedCommentDto = new CommentDto();
        updatedCommentDto.setId(1L);
        updatedCommentDto.setContent(updatedContent);
        updatedCommentDto.setAuthorId(1L);
        updatedCommentDto.setTaskId(1L);
        updatedCommentDto.setCreatedAt(LocalDateTime.now());

        comment.setContent(updatedContent);

        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(commentMapper.toCommentDto(comment)).thenReturn(updatedCommentDto);

        CommentDto result = commentService.updateContent(1L, updatedContent);

        assertNotNull(result);
        assertEquals(updatedContent, result.getContent(), "Content should be updated to: " + updatedContent);
        assertEquals(updatedCommentDto.getId(), result.getId());
        assertEquals(updatedCommentDto.getAuthorId(), result.getAuthorId());
        assertEquals(updatedCommentDto.getTaskId(), result.getTaskId());

        verify(commentRepository, times(1)).findById(1L);
        verify(commentRepository, times(1)).save(any(Comment.class));
        verify(commentMapper, times(1)).toCommentDto(comment);
    }


    @Test
    void updateContent_shouldThrowEntityNotFoundException_whenCommentDoesNotExist() {
        String updatedContent = "Updated comment content";

        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> commentService.updateContent(1L, updatedContent));

        verify(commentRepository, times(1)).findById(1L);
    }
}
