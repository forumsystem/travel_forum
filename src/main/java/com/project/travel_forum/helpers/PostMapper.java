package com.project.travel_forum.helpers;

import com.project.travel_forum.models.Post;
import com.project.travel_forum.models.PostDto;
import com.project.travel_forum.models.User;
import com.project.travel_forum.services.PostService;
import com.project.travel_forum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    private final PostService postService;

    @Autowired
    public PostMapper(PostService postService) {
        this.postService = postService;
    }

    public Post fromDto(int id, PostDto dto) {
        Post existingPost = postService.getById(id);

        existingPost.setTitle(dto.getTitle());
        existingPost.setContent(dto.getContent());

        return existingPost;
    }

    public Post fromDto(PostDto dto) {
        Post post = new Post();

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());

        return post;
    }

}
