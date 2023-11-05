package com.project.travel_forum.repositories;

import com.project.travel_forum.models.FilterOptions;
import com.project.travel_forum.models.Post;

import java.util.List;

public interface PostRepository {
    long getPostCount();
    List<Post> get(FilterOptions filterOptions);

    Post getById(int id);

    List<Post> getTop10MostCommented();

    List<Post> getTop10MostRecent();

    List<Post> getTop10MostLiked();

    void createPost(Post post);

    void updatePost(Post post);

    void deletePost(int id);

    void modifyLike(Post post);
}
