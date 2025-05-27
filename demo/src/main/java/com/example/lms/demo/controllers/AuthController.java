package com.example.lms.demo.controllers;

import com.example.lms.demo.models.User;
import com.example.lms.demo.models.Admin;
import com.example.lms.demo.repositories.UserRepository;
import com.example.lms.demo.repositories.AdminRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AuthController(UserRepository userRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("user") User user, Model model) {
        if (userRepository.findAll().stream().anyMatch(u -> u.getUsername().equals(user.getUsername()))) {
            model.addAttribute("error", "Username already taken.");
            return "signup";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "redirect:/auth/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User formUser, HttpSession session, Model model) {

       
        // Then, check if credentials match a normal user
        Optional<User> userOptional = userRepository.findAll().stream()
                .filter(u -> u.getUsername().equals(formUser.getUsername()))
                .findFirst();

        if (userOptional.isPresent() &&
                passwordEncoder.matches(formUser.getPassword(), userOptional.get().getPassword())) {
            session.setAttribute("loggedInUser", userOptional.get());
            return "redirect:/member/home";
        }

        model.addAttribute("error", "Invalid credentials.");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }
}
