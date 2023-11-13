package com.project.travel_forum.controllers.mvc;

import com.project.travel_forum.controllers.AuthenticationHelper;
import com.project.travel_forum.exceptions.AuthorizationException;
import com.project.travel_forum.exceptions.EntityNotFoundException;
import com.project.travel_forum.exceptions.UnauthorizedOperationException;
import com.project.travel_forum.helpers.CommentMapper;
import com.project.travel_forum.helpers.PostMapper;
import com.project.travel_forum.models.*;
import com.project.travel_forum.services.CommentService;
import com.project.travel_forum.services.PostService;
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

@Controller
@RequestMapping("/posts")
public class PostMvcController {
    private final PostService postService;
    private final PostMapper postMapper;
    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public PostMvcController(PostService postService, PostMapper postMapper, CommentService commentService, CommentMapper commentMapper, AuthenticationHelper authenticationHelper) {
        this.postService = postService;
        this.postMapper = postMapper;
        this.commentService = commentService;
        this.commentMapper = commentMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }


    @ModelAttribute("requestURI")
    public String getRequestURI(HttpServletRequest request) {
        return request.getRequestURI();
    }


    @GetMapping
    public String showAllPosts(@ModelAttribute("filterOptions") FilterDto filterDto,
                               Model model, HttpSession httpSession) {
        FilterOptions filterOptions = new FilterOptions(
                filterDto.getTitle(),
                filterDto.getContent(),
                filterDto.getCreatedBy(),
                filterDto.getSortBy(),
                filterDto.getSortOrder());
        try {
            authenticationHelper.tryGetCurrentUser(httpSession);
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        }
        List<Post> posts = postService.get(filterOptions);
        model.addAttribute("posts", posts);
        model.addAttribute("filterOptions", filterDto);
        return "AllPostsView";
    }

    @GetMapping("/{id}")
    public String showPost(@PathVariable int id, Model model, HttpSession httpSession) {
        User user;
        try {
            user = authenticationHelper.tryGetCurrentUser(httpSession);
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        }
        try {
            Post post = postService.getById(id);
            model.addAttribute("post", post);
            model.addAttribute("comment", new CommentDto());
            return "PostView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
    }

    @GetMapping("/new")
    public String createPostForm(Model model, HttpSession httpSession) {
        try {
            authenticationHelper.tryGetCurrentUser(httpSession);
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        }
        model.addAttribute("post", new PostDto());
        return "PostCreateView";
    }

    @PostMapping("/new")
    public String createPost(@Valid @ModelAttribute("post") PostDto postDto,
                             BindingResult result,
                             Model model,
                             HttpSession httpSession) {
        User user;
        try {
            user = authenticationHelper.tryGetCurrentUser(httpSession);
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        }
        if (result.hasErrors()) {
            return "PostCreateView";
        }

        try {
            Post post = postMapper.fromDto(postDto);
            postService.createPost(post, user);
            return "redirect:/posts";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        }
    }

    @GetMapping("/{id}/update")
    public String showPostEditPage(@PathVariable int id,
                                   Model model,
                                   HttpSession httpSession) {
        try {
            authenticationHelper.tryGetCurrentUser(httpSession);
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        }
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
        }
    }

    @PostMapping("/{id}/update")
    public String updatePost(@PathVariable int id,
                             @Valid @ModelAttribute("post") PostDto postDto,
                             Model model,
                             BindingResult result,
                             HttpSession httpSession) {
        User user;
        try {
            user = authenticationHelper.tryGetCurrentUser(httpSession);
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        }
        if (result.hasErrors()) {
            return "PostUpdateView";
        }
        try {
            Post post = postMapper.fromDto(id, postDto);
            postService.updatePost(post, user);
            return "redirect:/posts/{id}";
        } catch (AuthorizationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "redirect:/auth/login";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
    }

    @GetMapping("/{id}/delete")
    public String deletePost(@PathVariable int id,
                             Model model,
                             HttpSession httpSession) {
        User user;
        try {
            user = authenticationHelper.tryGetCurrentUser(httpSession);
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        }
        try {
            postService.deletePost(id, user);
            return "redirect:/posts";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        } catch (AuthorizationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "redirect:/auth/login";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
    }

    @PostMapping("/{id}/like")
    public String modifyLike(@PathVariable int id, HttpSession httpSession) {
        User user;
        Post post;
        try {
            user = authenticationHelper.tryGetCurrentUser(httpSession);
            post = postService.getById(id);
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        } catch (EntityNotFoundException e) {
            return "redirect:/posts";
        }
        if (post.getUserLikes().contains(user)) {
            postService.modifyLike(id, user, false);
        } else {
            postService.modifyLike(id, user, true);
        }
        return "redirect:/posts/{id}";
    }

    @PostMapping("/{id}/comment")
    public String addComment(@PathVariable int id,
                             @Valid @ModelAttribute CommentDto commentDto,
                             Model model,
                             HttpSession httpSession) {
        User user;
        Post post;
        Comment comment;
        try {
            user = authenticationHelper.tryGetCurrentUser(httpSession);
            comment = commentMapper.fromDto(commentDto);
            post = postService.getById(id);

            comment.setPost(post);
            comment.setCreatedBy(user);

            commentService.create(id, user, comment);
            return "redirect:/posts/" + id;
        } catch (AuthorizationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "redirect:/auth/login";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
    }

    @PostMapping("/{id}/comment/{commentId}/update")
    public String updateComment(@PathVariable int id,
                                @PathVariable int commentId,
                                @Valid @ModelAttribute CommentDto commentDto,
                                Model model,
                                BindingResult result,
                                HttpSession httpSession) {

        User user;
        Comment comment;

        try {
            user = authenticationHelper.tryGetCurrentUser(httpSession);
        } catch (AuthorizationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "redirect:/auth/login";
        }
        if (result.hasErrors()) {
            return "CommentUpdateView";
        }

        try {
            comment = commentMapper.fromDto(commentId, commentDto);
            commentService.update(comment, user);
            return "redirect:/posts/{id}";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
    }

    @GetMapping("/{id}/comment/{commentId}/update")
    public String showCommentEditPage(@PathVariable int id,
                                      @PathVariable int commentId,
                                      Model model,
                                      HttpSession httpSession) {
        try {
            authenticationHelper.tryGetCurrentUser(httpSession);
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        }
        try {
            Comment comment = commentService.getByCommentId(commentId);
            CommentDto commentDto = commentMapper.toDto(comment);
            model.addAttribute("commentId", commentId);
            model.addAttribute("comment", commentDto);
            return "CommentUpdateView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
    }

    @GetMapping("/{id}/comment/{commentId}/delete")
    public String deleteComment(@PathVariable int id,
                                @PathVariable int commentId,
                                Model model,
                                HttpSession httpSession) {
        User user;
        Comment comment = commentService.getByCommentId(commentId);
        try {
            user = authenticationHelper.tryGetCurrentUser(httpSession);
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        }
        try {
            commentService.delete(commentId, user, comment.getCreatedBy());
            return "redirect:/posts/" + id;
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        } catch (AuthorizationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "redirect:/auth/login";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
    }

}