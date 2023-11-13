package com.project.travel_forum.controllers.mvc;

import com.project.travel_forum.controllers.AuthenticationHelper;
import com.project.travel_forum.exceptions.AuthorizationException;
import com.project.travel_forum.helpers.UserMapper;
import com.project.travel_forum.models.*;
import com.project.travel_forum.services.PostService;
import com.project.travel_forum.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/")
@Controller
public class HomeController {
    private final AuthenticationHelper authenticationHelper;
    private final PostService postService;
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public HomeController(AuthenticationHelper authenticationHelper, PostService postService, UserService userService, UserMapper userMapper) {
        this.authenticationHelper = authenticationHelper;
        this.postService = postService;
        this.userService = userService;
        this.userMapper = userMapper;
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
    public String showAdminPortal(@ModelAttribute("filterUserOptions") FilterUserDto filterUserDto,
                                  HttpSession session) {
        try {
            User user = authenticationHelper.tryGetCurrentUser(session);
            if (user.isAdmin()) {
                return "AdminPortalVieww";
            }
            return "ErrorView";
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        }
    }

    @GetMapping("/settings")
    public String showSettings(HttpSession session, Model model) {
        User user;
        try {
            user = authenticationHelper.tryGetCurrentUser(session);

            UpdateUserDto updateUserDto = new UpdateUserDto();
            updateUserDto.setFirstName(user.getFirstName());
            updateUserDto.setLastName(user.getLastName());
            updateUserDto.setEmail(user.getEmail());
            updateUserDto.setPassword(user.getPassword());
            updateUserDto.setPasswordConfirm(user.getPassword());

            model.addAttribute("updateUser", updateUserDto);
            model.addAttribute("currentUser", user);
//            model.addAttribute("userDto", new UpdateUserDto());
            return "Settings";
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        }
    }

        @PostMapping("/settings/update")
    public String updateUser(
//            @PathVariable int id,
            @ModelAttribute("updateUser") UpdateUserDto updateUserDto,
            BindingResult result,
            Model model,
            HttpSession httpSession) {
        User user;
        try {
            if (result.hasErrors()) {
                return "Settings";
            }
            user = authenticationHelper.tryGetCurrentUser(httpSession);
            User userToUpdate = userMapper.fromDto(user.getId(), updateUserDto, user);
            userService.updateUserV2(user, userToUpdate, updateUserDto);
//            model.addAttribute("updateUser", userToUpdate);
            return "redirect:/settings";
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

//    @PostMapping("/settings/update")
//    public String showUserUpdatePage(HttpSession session, Model model) {
//        try {
//            User currentUser = authenticationHelper.tryGetCurrentUser(session);
//            model.addAttribute("currentUser", currentUser);
//            RegisterDto registerDto = new RegisterDto();
//            registerDto.setUsername(currentUser.getUsername());
//            registerDto.setFirstName(currentUser.getFirstName());
//            registerDto.setLastName(currentUser.getLastName());
//            registerDto.setEmail(currentUser.getEmail());
//            registerDto.setPassword(currentUser.getPassword());
//            registerDto.setPasswordConfirm(currentUser.getPassword());
//
//            model.addAttribute("userDto", registerDto);
//            return "Settings";
//        } catch (AuthorizationException e) {
//            return "redirect:/auth/login";
//        }
//    }


}
