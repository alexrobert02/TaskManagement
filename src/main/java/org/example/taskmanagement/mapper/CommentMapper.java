package org.example.taskmanagement.mapper;

import org.example.taskmanagement.dto.CommentDto;
import org.example.taskmanagement.model.Comment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentDto toCommentDto(Comment comment);

    List<CommentDto> toCommentDtoList(List<Comment> comments);
}

