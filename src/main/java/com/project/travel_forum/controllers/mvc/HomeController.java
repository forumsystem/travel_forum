package com.project.travel_forum.controllers.mvc;

import com.project.travel_forum.controllers.AuthenticationHelper;
import com.project.travel_forum.exceptions.AuthorizationException;
import com.project.travel_forum.models.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class HomeController {
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public HomeController(AuthenticationHelper authenticationHelper) {
        this.authenticationHelper = authenticationHelper;
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
    public String showHomePage() {
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
