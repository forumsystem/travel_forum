package com.project.travel_forum.repositories;

import com.project.travel_forum.models.Post;

import java.util.List;

public interface PostRepository {
    List<Post> getAll();
    Post getById(int id);
    void createPost(Post post);
    void updatePost(Post post);
    void deletePost(int id);

}
