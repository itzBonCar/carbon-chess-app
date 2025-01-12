package com.carbonchess11.carbon_chess_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThymeleafTestController {

    @GetMapping("/test")
    public String testThymeleaf(Model model) {
        model.addAttribute("currentTime", new java.util.Date());
        return "test"; // Render the "test.html" template
    }
}
