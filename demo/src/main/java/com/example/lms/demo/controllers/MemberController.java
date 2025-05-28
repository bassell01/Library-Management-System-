package com.example.lms.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {

    @GetMapping("/member/home")
    public String memberHome(HttpSession session, Model model) {
        Object user = session.getAttribute("user");
        if (user == null) {
            return "redirect:/auth/login"; // لو مش مسجل دخول يرجع للّوجين
        }
        model.addAttribute("user", user);
        return "member-home"; // صفحة member-home.html
    }
}
