package com.project.travel_forum.services;

import com.project.travel_forum.models.FilterOptions;
import com.project.travel_forum.models.Post;
import com.project.travel_forum.models.User;

import java.util.List;

public interface PostService {
    List<Post> get(FilterOptions filterOptions);
    Post getById(int id);
    void createPost(Post post, User user);
    void updatePost(Post post, User user);
    void deletePost(int id, User user);
}
