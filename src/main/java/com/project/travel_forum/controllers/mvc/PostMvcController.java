package com.project.travel_forum.controllers.mvc;

import com.project.travel_forum.exceptions.EntityNotFoundException;
import com.project.travel_forum.models.FilterOptions;
import com.project.travel_forum.models.Post;
import com.project.travel_forum.services.PostService;
import org.hibernate.action.internal.EntityActionVetoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/posts")
@Controller
public class PostMvcController {
    private final PostService postService;

    @Autowired
    public PostMvcController(PostService postService) {
        this.postService = postService;
    }
    @GetMapping
    public String showAllPosts(Model model){
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

}
