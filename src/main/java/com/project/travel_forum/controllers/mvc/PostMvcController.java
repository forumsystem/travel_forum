package com.project.travel_forum.controllers.mvc;

import com.project.travel_forum.exceptions.AuthorizationException;
import com.project.travel_forum.exceptions.EntityDuplicateException;
import com.project.travel_forum.exceptions.EntityNotFoundException;
import com.project.travel_forum.exceptions.UnauthorizedOperationException;
import com.project.travel_forum.helpers.PostMapper;
import com.project.travel_forum.models.FilterOptions;
import com.project.travel_forum.models.Post;
import com.project.travel_forum.models.PostDto;
import com.project.travel_forum.models.User;
import com.project.travel_forum.services.PostService;
import com.project.travel_forum.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/posts")
@Controller
public class PostMvcController {
    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;

    @Autowired
    public PostMvcController(PostService postService, PostMapper postMapper, UserService userService) {
        this.postService = postService;
        this.postMapper = postMapper;
        this.userService = userService;
    }

    @ModelAttribute("requestURI")
    public String getRequestURI(HttpServletRequest request) {
        return request.getRequestURI();
    }


    @GetMapping
    public String showAllPosts(Model model) {
        FilterOptions filterOptions = new FilterOptions(null, null, null, null, null);
        List<Post> posts = postService.get(filterOptions);
        model.addAttribute("posts", posts);
        return "AllPostsView";
    }

    @GetMapping("/{id}")
    public String showPost(@PathVariable int id, Model model) {
        try {
            Post post = postService.getById(id);
            model.addAttribute("post", post);
            return "PostView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("statusCode", 404);
            return "ErrorView";
        }
    }

    @GetMapping("/new")
    public String createPostForm(Model model) {
        model.addAttribute("post", new PostDto());
        return "PostCreateView";
    }

    @PostMapping("/new")
    public String createPost(@Valid @ModelAttribute("post") PostDto postDto,
                             Model model, BindingResult result) {
        if (result.hasErrors()) {
            return "PostCreateView";
        }

        try {
            //TODO get authenticated user from the HTTP session next week we finish this

            User user = userService.get(1);
            Post post = postMapper.fromDto(postDto);
            postService.createPost(post, user);
            return "redirect:/posts";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
        // -- TODO --  1 hours:32 min
        catch (UnauthorizedOperationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
    }

    @GetMapping("/{id}/update")
    public String showPostEditPage(@PathVariable int id, Model model, HttpSession session) {
        try {
            Post post = postService.getById(id);
            PostDto postDto = postMapper.toDto(post);
            model.addAttribute("postId", id);
            model.addAttribute("post", postDto);
            return "PostUpdateView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
    }

    @PostMapping("/{id}/update")
    public String updatePost(@PathVariable int id,
                             @Valid @ModelAttribute("post") PostDto postDto,
                             Model model, BindingResult result) {
        if (result.hasErrors()) {
            return "PostUpdateView";
        }
        try {
            User user = userService.get(1);
            Post post = postMapper.fromDto(1, postDto);
            postService.updatePost(post, user);
            return "redirect:/posts/{id}/update";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        } catch (EntityDuplicateException e) {
            model.addAttribute("statusCode", HttpStatus.CONFLICT.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        } catch (AuthorizationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
    }

    @GetMapping("/{id}/delete")
    public String deletePost(@PathVariable int id, Model model) {
        try {
            User user = userService.get(1);
            postService.deletePost(id, user);
            return "redirect:/posts";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        } catch (AuthorizationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
    }

}
