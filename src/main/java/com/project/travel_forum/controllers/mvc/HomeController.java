package com.project.travel_forum.controllers.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class HomeController {
    @GetMapping("/about")
    public String showAboutPage() {
        return "About";
    }


    @GetMapping
    public String showHomePage() {
    return "HomeView";
}






}
