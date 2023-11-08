package com.project.travel_forum.controllers.mvc;

import com.project.travel_forum.controllers.AuthenticationHelper;
import com.project.travel_forum.exceptions.AuthorizationException;
import com.project.travel_forum.models.Post;
import com.project.travel_forum.models.User;
import com.project.travel_forum.services.PostService;
import com.project.travel_forum.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/")
@Controller
public class HomeController {
    private final AuthenticationHelper authenticationHelper;
    private final PostService postService;
    private final UserService userService;

    @Autowired
    public HomeController(AuthenticationHelper authenticationHelper, PostService postService, UserService userService) {
        this.authenticationHelper = authenticationHelper;
        this.postService = postService;
        this.userService = userService;
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
        long postCount = postService.getPostCount();
        model.addAttribute("postCount", postCount);

        long userCount = userService.getUserCount();
        model.addAttribute("userCount", userCount);

        List<Post> mostRecent = postService.getTop10MostRecent();
        model.addAttribute("lastPost", mostRecent);

        List<Post> mostCommented = postService.getTop10MostCommented();
        model.addAttribute("comments", mostCommented);

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
