package com.project.travel_forum.services;

import com.project.travel_forum.models.Post;
import com.project.travel_forum.models.User;
import com.project.travel_forum.repositories.PostRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    // -- TODO --
    // not autowired in Beans

    private final PostRepositoryImpl postRepository;

    @Autowired
    public PostServiceImpl(PostRepositoryImpl postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> get() {
        return null;
    }

    @Override
    public Post getById(int id) {
        return null;
    }

    @Override
    public void createPost(Post post, User user) {

    }

    @Override
    public void updatePost(Post post, User user) {

    }

    @Override
    public void deletePost(int id, User user) {

    }
}
