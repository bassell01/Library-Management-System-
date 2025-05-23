package com.example.lms.demo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.lms.demo.models.User;
import com.example.lms.demo.repositories.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        
        User user = (User) session.getAttribute("loggedInUser");

        
        if (user == null) {
            return "redirect:/login";
        }

        
        model.addAttribute("user", user);

        return "dashboard"; 
    }

    @GetMapping("/dashboard/{userId}")
    public String dashboard(@PathVariable Long userId, Model model) {
        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
            return "dashboard";  
        } else {
            return "error";  
        
        }
    }
}