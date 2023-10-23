package com.project.travel_forum.helpers;

import com.project.travel_forum.models.Comment;
import com.project.travel_forum.models.CommentDto;
import com.project.travel_forum.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    private  final CommentService commentService;
    @Autowired
    public CommentMapper(CommentService commentService) {
        this.commentService = commentService;
    }
    public Comment fromDto(int id, CommentDto dto){
        Comment existingComment=commentService.getByCommentId(id);

        existingComment.setComment(dto.getComment());
        return existingComment;
    }
    public Comment fromDto(CommentDto dto){
        Comment comment=new Comment();
        comment.setComment(dto.getComment());
        return comment;
    }

}
