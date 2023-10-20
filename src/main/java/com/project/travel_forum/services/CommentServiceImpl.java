package com.project.travel_forum.services;

import com.project.travel_forum.exceptions.AuthorizationException;
import com.project.travel_forum.exceptions.EntityNotFoundException;
import com.project.travel_forum.models.Comment;
import com.project.travel_forum.models.Post;
import com.project.travel_forum.models.User;
import com.project.travel_forum.repositories.CommentRepository;
import com.project.travel_forum.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.travel_forum.helpers.CheckPermissions.checkIfBlocked;
import static com.project.travel_forum.helpers.CheckPermissions.checkIfSameUser;

@Service
public class CommentServiceImpl implements CommentService {
    private  final CommentRepository commentRepository;
    private  final PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }
    @Override
    public Comment getById(int id) {
        return commentRepository.getById(id);
    }

    @Override
    public List<Comment> get(Post post) {
        return null;
    }

    @Override
    public void create(int postId, User user,Comment content) {
        checkIfBlocked(user);
        Post post=postRepository.getById(postId);
        if(post==null){
            throw new EntityNotFoundException("Post with id not found: ",postId);
        }
        content.setComment(content.getComment());
        content.setCreatedBy(user);
        content.setPost(post);

        commentRepository.create(content);
    }

    @Override
    public void update(Comment comment,User user) {
        checkIfSameUser(comment.getCreatedBy(),user);
        commentRepository.update(comment);
    }

    @Override
    public void delete(int id,User user) {
        checkModifyPermissions(id, user);
        commentRepository.delete(id);
    }
    private void checkModifyPermissions(int id, User user) {
        Comment comment = commentRepository.getById(id);
        if (!(user.isAdmin() || comment.getCreatedBy().equals(user))) {
            throw new AuthorizationException("You don't have such permission!");
        }
    }

}
