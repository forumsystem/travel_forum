package com.project.travel_forum.services;

import com.project.travel_forum.models.Comment;
import com.project.travel_forum.models.Post;
import com.project.travel_forum.models.User;
import com.project.travel_forum.repositories.CommentRepository;
import com.project.travel_forum.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.travel_forum.helpers.CheckPermissions.*;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Comment getByCommentId(int id) {
        return commentRepository.getByCommentId(id);
    }

    @Override
    public List<Comment> getByPost(Post post) {
        return commentRepository.getByPost(post);
    }

    @Override
    public List<Comment> getByUser(User user) {
        return commentRepository.getByUser(user);
    }

    @Override
    public void create(int postId, User user, Comment comment) {
        checkIfBlocked(user);
        Post post = postRepository.getById(postId);
        comment.setCreatedBy(user);
        comment.setPost(post);
        commentRepository.create(comment);
    }

    @Override
    public void update(Comment comment, User user) {
        checkIfSameUser(comment.getCreatedBy(), user);
        commentRepository.update(comment);
    }

    @Override
    public void delete(int id,User user) {
        checkUserAuthorization(id, user);
        commentRepository.delete(id);
    }


}
