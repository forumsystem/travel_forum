package com.project.travel_forum.repositories;

import com.project.travel_forum.models.Comment;
import com.project.travel_forum.models.Post;
import com.project.travel_forum.models.User;

import java.util.List;

public interface CommentRepository {

    List<Comment> getByPost(Post post);

    List<Comment> getByUser(User user);

    Comment getByCommentId(int id);

    void create(Comment comment);

    void update(Comment comment);

    void delete(int id);

    void deleteAllCommentsByPost(Post post);
}
