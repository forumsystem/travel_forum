package com.project.travel_forum.controllers.mvc;

import com.project.travel_forum.models.Post;
import com.project.travel_forum.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/posts")
@Controller
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{id}")
    public String ShowPost(@PathVariable int id, Model model){
       Post post = postService.getById(id);
       model.addAttribute("post", post);
       return "PostView";
    }
}
