package org.example.taskmanagement.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.dto.CommentCreationRequestDto;
import org.example.taskmanagement.dto.CommentDto;
import org.example.taskmanagement.mapper.CommentMapper;
import org.example.taskmanagement.model.Comment;
import org.example.taskmanagement.repository.CommentRepository;
import org.example.taskmanagement.repository.TaskRepository;
import org.example.taskmanagement.repository.UserRepository;
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
