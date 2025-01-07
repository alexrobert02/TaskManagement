package org.example.taskmanagement.mapper;

import org.example.taskmanagement.dto.CommentCreationRequestDto;
import org.example.taskmanagement.dto.CommentDto;
import org.example.taskmanagement.model.Comment;
import org.example.taskmanagement.model.Task;
import org.example.taskmanagement.model.User;
import org.example.taskmanagement.repository.TaskRepository;
import org.example.taskmanagement.repository.UserRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Comparator;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "taskId", source = "task.id")
    CommentDto toCommentDto(Comment comment);

    @Mapping(target = "author", source = "authorId", qualifiedByName = "mapAuthor")
    @Mapping(target = "task", source = "taskId", qualifiedByName = "mapTask")
    Comment toComment(CommentCreationRequestDto commentCreationRequestDto,
                @Context UserRepository userRepository,
                @Context TaskRepository taskRepository);

    @Named("mapAuthor")
    default User mapAuthor(Long authorId, @Context UserRepository userRepository) {
        return userRepository.findById(authorId).orElse(null);
    }

    @Named("mapTask")
    default Task mapTask(Long taskId, @Context TaskRepository taskRepository) {
        return taskRepository.findById(taskId).orElse(null);
    }

    default List<CommentDto> toCommentDtoList(List<Comment> comments) {
        return comments.stream()
                .sorted(Comparator.comparing(Comment::getCreatedAt))
                .map(this::toCommentDto)
                .toList();
    }
}

