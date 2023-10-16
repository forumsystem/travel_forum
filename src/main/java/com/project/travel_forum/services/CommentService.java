package com.project.travel_forum.services;

import com.project.travel_forum.models.Comment;
import com.project.travel_forum.models.Post;

import java.util.List;

public interface CommentService {
    List<Comment> get(Post post);

    void create(Comment comment);

    void update(Comment comment);

    void delete(Comment comment);

}
