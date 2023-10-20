package com.project.travel_forum.repositories;

import com.project.travel_forum.models.Comment;
import com.project.travel_forum.models.Post;

import java.util.List;

public interface CommentRepository {

    List<Comment> getByPost(Post post);

    Comment getById(int id);

    void create(Comment comment);

    void update(Comment comment);

    void delete(int id);
}
