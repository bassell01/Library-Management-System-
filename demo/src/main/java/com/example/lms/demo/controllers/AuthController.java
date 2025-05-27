package com.example.lms.demo.controllers;

import com.example.lms.demo.dtos.LoginRequest;
import com.example.lms.demo.dtos.SignupRequest;
import com.example.lms.demo.models.User;
import com.example.lms.demo.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller

@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new SignupRequest());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("user") @Valid SignupRequest signupRequest,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            return "signup";
        }

        if (userRepository.findByUsername(signupRequest.getUsername()).isPresent()) {
            model.addAttribute("error", "Username already taken.");
            return "signup";
        }

        User user = new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setPhone(signupRequest.getPhone());
        user.setAddress(signupRequest.getAddress());
        user.setUsername(signupRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        userRepository.save(user);
        return "redirect:/auth/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new LoginRequest());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") @Valid LoginRequest loginRequest,
                        BindingResult result,
                        HttpSession session,
                        Model model) {

        if (result.hasErrors()) {
            return "login";
        }

        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());

        if (userOptional.isPresent() &&
            passwordEncoder.matches(loginRequest.getPassword(), userOptional.get().getPassword())) {
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
