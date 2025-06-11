package com.taskmanagement.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import com.taskmanagement.dto.CommentCreationRequestDto;
import com.taskmanagement.dto.CommentDto;
import com.taskmanagement.mapper.CommentMapper;
import com.taskmanagement.model.Comment;
import com.taskmanagement.repository.CommentRepository;
import com.taskmanagement.repository.TaskRepository;
import com.taskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    private final CommentMapper commentMapper;

    private final CommentRepository commentRepository;

    public CommentDto createComment(CommentCreationRequestDto commentCreationRequestDto) {

        Comment comment = commentMapper.toComment(commentCreationRequestDto, userRepository, taskRepository);
        return commentMapper.toCommentDto(commentRepository.save(comment));
    }

    public List<CommentDto> getAllComments() {
        return commentRepository.findAll().stream()
                .map(commentMapper::toCommentDto)
                .toList();
    }

    public CommentDto updateContent(Long id, String content) {
        return commentRepository.findById(id)
                .map(comment -> {
                    comment.setContent(content);
                    return commentMapper.toCommentDto(commentRepository.save(comment));
                }).orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + id));
    }

}
