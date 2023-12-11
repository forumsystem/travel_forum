package com.project.travel_forum.controllers.mvc;

import com.project.travel_forum.controllers.AuthenticationHelper;
import com.project.travel_forum.exceptions.AuthorizationException;
import com.project.travel_forum.exceptions.EntityNotFoundException;
import com.project.travel_forum.exceptions.UnauthorizedOperationException;
import com.project.travel_forum.models.FilterUserDto;
import com.project.travel_forum.models.FilterUserOptions;
import com.project.travel_forum.models.User;
import com.project.travel_forum.services.PhoneNumberService;
import com.project.travel_forum.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserMvcController {
    private final UserService userService;
    private final PhoneNumberService phoneNumberService;
    private final AuthenticationHelper authenticationHelper;

    public UserMvcController(UserService userService, PhoneNumberService phoneNumberService,
                             AuthenticationHelper authenticationHelper) {
        this.userService = userService;
        this.phoneNumberService = phoneNumberService;
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
        } catch (EntityNotFoundException e) {
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
        } catch (EntityNotFoundException e) {
            return "BlockUserView";
        }

    }

    @GetMapping("/{id}/block")
    public String blockUser(@PathVariable int id, Model model, HttpSession httpSession) {
        User user;
        User userToBlock = userService.get(id);
        try {
            user = authenticationHelper.tryGetCurrentUser(httpSession);
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        }
        if (userToBlock.isBlocked()) {
            try {
                userService.modifyBlock(id, user, false);
            } catch (UnauthorizedOperationException e) {
                model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
                model.addAttribute("error", e.getMessage());
                return "ErrorView";
            }
        } else {
            try {
                userService.modifyBlock(id, user, true);
            } catch (UnauthorizedOperationException e) {
                model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
                model.addAttribute("error", e.getMessage());
                return "ErrorView";
            }
        }
        return "redirect:/users/block";
    }

    @GetMapping("/{id}/admin")
    public String makeAdmin(@PathVariable int id, Model model, HttpSession httpSession) {
        User user;
        User userToMakeAdmin = userService.get(id);
        try {
            user = authenticationHelper.tryGetCurrentUser(httpSession);
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        }
        if (userToMakeAdmin.isAdmin()) {
            try {
                userService.modifyPermissions(id, user, false);
            } catch (UnauthorizedOperationException e) {
                model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
                model.addAttribute("error", e.getMessage());
                return "ErrorView";
            }
        } else {
            try {
                userService.modifyPermissions(id, user, true);
            } catch (UnauthorizedOperationException e) {
                model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
                model.addAttribute("error", e.getMessage());
                return "ErrorView";
            }
        }
        return "redirect:/users/admins";
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
        } catch (EntityNotFoundException e) {
            return "Admins";
        }

    }
}