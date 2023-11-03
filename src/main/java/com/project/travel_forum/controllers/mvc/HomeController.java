package com.project.travel_forum.controllers.mvc;

import com.project.travel_forum.controllers.AuthenticationHelper;
import com.project.travel_forum.exceptions.AuthorizationException;
import com.project.travel_forum.models.Post;
import com.project.travel_forum.models.User;
import com.project.travel_forum.services.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/")
@Controller
public class HomeController {
    private final AuthenticationHelper authenticationHelper;
    private final PostService postService;

    @Autowired
    public HomeController(AuthenticationHelper authenticationHelper, PostService postService) {
        this.authenticationHelper = authenticationHelper;
        this.postService = postService;
    }

    @GetMapping("/about")
    public String showAboutPage() {
        return "About";
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @GetMapping
    public String showHomePage(Model model) {
        List<Post> mostCommented = postService.getTop10MostCommented();
        model.addAttribute("lastPost", mostCommented);

        List<Post> mostRecent = postService.getTop10MostResent();
        model.addAttribute("comments", mostRecent);


        return "HomeView";
    }

    @GetMapping("/admin")
    public String showAdminPortal(HttpSession session, Model model) {
        try {
            User user = authenticationHelper.tryGetCurrentUser(session);
            if (user.isAdmin()) {
                return "AdminPortalView";
            }
            return "AccessDeniedView";
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        }
    }


}
