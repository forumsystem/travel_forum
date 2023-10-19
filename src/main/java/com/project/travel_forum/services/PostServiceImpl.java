package com.project.travel_forum.services;

import com.project.travel_forum.exceptions.AuthorizationException;
import com.project.travel_forum.exceptions.EntityNotFoundException;
import com.project.travel_forum.exceptions.UnauthorizedOperationException;
import com.project.travel_forum.models.Post;
import com.project.travel_forum.models.User;
import com.project.travel_forum.repositories.PostRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    public static final String USER_IS_NOT_ADMIN = "This user is not admin";
    public static final String BLOCKED_USER_CREATE_ERR = "Blocked users cannot create new posts.";

    // -- TODO --
    // not autowired in Beans

    private final PostRepositoryImpl postRepository;

    @Autowired
    public PostServiceImpl(PostRepositoryImpl postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getAll() {
        return postRepository.getAll();
    }

    @Override
    public Post getById(int id) {
        return postRepository.getById(id);
    }

    @Override
    public void createPost(Post post, User user) {

        if (user.isBlocked()) {
            throw new UnauthorizedOperationException(BLOCKED_USER_CREATE_ERR);
        }

        post.setCreatedBy(user);
        postRepository.createPost(post);
    }

    @Override
    public void updatePost(Post post, User user) {
        // -- TODO -- check if user method or block user
        postRepository.updatePost(post);
    }

    @Override
    public void deletePost(int id, User user) {
        // -- TODO -- check if admin or user - admin can delete every post
        postRepository.deletePost(id);
    }
}
