package com.project.travel_forum.services;

import com.project.travel_forum.models.Comment;
import com.project.travel_forum.models.Post;
import com.project.travel_forum.models.User;

import java.util.List;

public interface CommentService {
    Comment getByCommentId(int id);

    List<Comment> getByPost(Post post);

    List<Comment> getByUser(User user);

    void create(int postId, User user, Comment comment);

    void update(Comment comment, User user);

    void delete(int id, User user, User creator);

}
