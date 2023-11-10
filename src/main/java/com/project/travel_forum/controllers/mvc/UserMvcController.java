package com.project.travel_forum.controllers.mvc;

import com.project.travel_forum.controllers.AuthenticationHelper;
import com.project.travel_forum.exceptions.AuthorizationException;
import com.project.travel_forum.exceptions.EntityNotFoundException;
import com.project.travel_forum.exceptions.UnauthorizedOperationException;
import com.project.travel_forum.helpers.PostMapper;
import com.project.travel_forum.models.*;
import com.project.travel_forum.services.PostService;
import com.project.travel_forum.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserMvcController {
    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;

    public UserMvcController(UserService userService, AuthenticationHelper authenticationHelper) {
        this.userService = userService;
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
    public String showAllUsers(@ModelAttribute("filterUserOptions") FilterUserDto filterUserDto,
                               Model model, HttpSession httpSession) {
        User user;
        FilterUserOptions filterUserOptions = new FilterUserOptions(
                filterUserDto.getUsername(),
                filterUserDto.getEmail(),
                filterUserDto.getFirstName());
        try {
            user = authenticationHelper.tryGetCurrentUser(httpSession);
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        }
        try {
            List<User> users = userService.get(user, filterUserOptions);
            model.addAttribute("users", users);
            model.addAttribute("filterUserOptions", filterUserDto);
            return "AllUsersView";
        }catch (EntityNotFoundException e){
            return "AllUsersView";
        }

    }

    @GetMapping("/block")
    public String showBlockUsers(Model model,
                                 HttpSession httpSession) {

        try {
            authenticationHelper.tryGetCurrentUser(httpSession);
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        }

        try {
            List<User> users = userService.getAllBlockUser();
            model.addAttribute("users", users);
            return "BlockUserView";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }catch (EntityNotFoundException e){
            return "BlockUserView";
        }

    }

    @GetMapping("/{id}/block")
    public String blockUser(@PathVariable int id, HttpSession httpSession) {
        User user;
        try {
            user = authenticationHelper.tryGetCurrentUser(httpSession);
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        }
        if (user.isBlocked()) {
            userService.modifyBlock(id, user, false);
        } else {
            userService.modifyBlock(id, user, true);
        }
        return "redirect:/users/block";
    }

    @GetMapping("/admins")
    public String showAdmins(Model model, HttpSession httpSession) {
        try {
            authenticationHelper.tryGetCurrentUser(httpSession);
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        }

        try {
            List<User> users = userService.getAllAdmins();
            model.addAttribute("users", users);
            return "Admins";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }catch (EntityNotFoundException e){
            return "Admins";
        }

    }
}

