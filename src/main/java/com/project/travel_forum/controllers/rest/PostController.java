package com.project.travel_forum.controllers.rest;

import com.project.travel_forum.controllers.AuthenticationHelper;
import com.project.travel_forum.exceptions.AuthorizationException;
import com.project.travel_forum.exceptions.EntityDuplicateException;
import com.project.travel_forum.exceptions.EntityNotFoundException;
import com.project.travel_forum.exceptions.UnauthorizedOperationException;
import com.project.travel_forum.helpers.PostMapper;
import com.project.travel_forum.models.*;
import com.project.travel_forum.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final AuthenticationHelper authenticationHelper;
    private final PostMapper postMapper;

    @Autowired
    public PostController(PostService postService, AuthenticationHelper authenticationHelper, PostMapper postMapper) {
        this.postService = postService;
        this.authenticationHelper = authenticationHelper;
        this.postMapper = postMapper;
    }


    //@ResponseEntity - this needs to be returned instead of <Post> there we structure the whole response back to PostMan
    //where we add the list of posts, count and offset - NOT AN URGENT TASK (good to have)
    @GetMapping
    public List<Post> get(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String createdBy,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder) {
        FilterOptions filterOptions = new FilterOptions(title, content, createdBy,
                sortBy, sortOrder);
        return postService.get(filterOptions);
    }

    @GetMapping("/{id}")
    public Post getById(@PathVariable int id) {
        try {
            return postService.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/home/comments")
    public List<Post> getTop10MostCommented() {
        return postService.getTop10MostCommented();
    }

    @GetMapping("/home/likes")
    public List<Post> getTop10MostLiked() {
        return postService.getTop10MostLiked();
    }

    @GetMapping("/home/recent")
    public List<Post> getTop10MostRecent() {
        return postService.getTop10MostRecent();
    }

    @PostMapping
    public Post create(@RequestHeader HttpHeaders headers, @Valid @RequestBody PostDto postDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Post post = postMapper.fromDto(postDto);
            postService.createPost(post, user);
            return post;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping
    public Post update(@RequestHeader HttpHeaders headers, @PathVariable int id, @Valid @RequestBody PostDto postDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Post post = postMapper.fromDto(id, postDto);
            postService.updatePost(post, user);
            return post;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            postService.deletePost(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PatchMapping("/{id}/like")
    public void modifyLike(@RequestHeader HttpHeaders headers, @PathVariable int id, @RequestParam boolean isLiked) {
        try {
            boolean likeFlag = isLiked;

            User user = authenticationHelper.tryGetUser(headers);
            postService.modifyLike(id, user, likeFlag);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}













