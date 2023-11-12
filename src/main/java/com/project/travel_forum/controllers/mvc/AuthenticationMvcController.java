package com.project.travel_forum.controllers.mvc;

import com.project.travel_forum.controllers.AuthenticationHelper;
import com.project.travel_forum.exceptions.AuthorizationException;
import com.project.travel_forum.exceptions.EntityDuplicateException;
import com.project.travel_forum.exceptions.EntityNotFoundException;
import com.project.travel_forum.exceptions.UnauthorizedOperationException;
import com.project.travel_forum.helpers.UserMapper;
import com.project.travel_forum.models.LoginDto;
import com.project.travel_forum.models.RegisterDto;
import com.project.travel_forum.models.User;
import com.project.travel_forum.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.http.HttpHeaders;

@Controller
@RequestMapping("/auth")
public class AuthenticationMvcController {

    private final UserService userService;

    private final AuthenticationHelper authenticationHelper;

    private final UserMapper userMapper;

    @Autowired
    public AuthenticationMvcController(UserService userService, AuthenticationHelper authenticationHelper, UserMapper userMapper) {
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;
        this.userMapper = userMapper;
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("login", new LoginDto());
        return "LoginView";
    }

    @PostMapping("/login")
    public String handleLogin(@Valid @ModelAttribute("login") LoginDto loginDto,
                              BindingResult bindingResult,
                              HttpSession httpSession) {

        if (bindingResult.hasErrors()) {
            return "LoginView";
        }

        try {
            User user = authenticationHelper.verifyAuthentication(loginDto.getUsername(), loginDto.getPassword());

            httpSession.setAttribute("currentUser", loginDto.getUsername());
            httpSession.setAttribute("isAdmin", user.isAdmin());
            return "redirect:/";
        } catch (AuthorizationException e) {
            bindingResult.rejectValue("username", "auth_error", e.getMessage());
            return "LoginView";
        }
    }

    @GetMapping("/logout")
    public String handleLogout(HttpSession session) {
        session.removeAttribute("currentUser");
        return "redirect:/";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("register", new RegisterDto());
        return "RegisterView";
    }

    @PostMapping("/register")
    public String handleRegister(@Valid @ModelAttribute("register") RegisterDto register,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "RegisterView";
        }

        if (!register.getPassword().equals(register.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "password_error", "Password confirmation should match password.");
            return "RegisterView";
        }

        try {
            User user = userMapper.fromDto(register);
            userService.createUser(user);
            return "redirect:/auth/login";
        } catch (EntityDuplicateException e) {
            bindingResult.rejectValue("username", "username_error", e.getMessage());
            return "RegisterView";
        }
    }
//    @PutMapping("/settings")
//    public String updateUser(
////            @PathVariable int id,
//            @Valid @ModelAttribute("registerDto") RegisterDto registerDto,
//            BindingResult result,
//            Model model,
//            HttpSession httpSession) {
//        User user;
//        try {
//            if (result.hasErrors()) {
//                return "Settings";
//            }
//
//            user = authenticationHelper.tryGetCurrentUser(httpSession);
//            User userToUpdate = userMapper.fromDto(user.getId(), registerDto, user);
//            userService.updateUser(user, userToUpdate);
//
//            model.addAttribute("user", userToUpdate);
//            return "redirect:/settings"; // Redirect to the user profile page with a success parameter
//        } catch (EntityNotFoundException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
//        } catch (EntityDuplicateException e) {
//            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
//        } catch (UnauthorizedOperationException e) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
//        }
//    }


}
