package com.example.lms.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.lms.demo.models.Transactions;
import com.example.lms.demo.models.User;
import com.example.lms.demo.services.TransactionsService;
import com.example.lms.demo.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionsService transactionsService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = userService.getLoggedInUser(session);

        if (user == null) {
            return "redirect:/auth/login";
        }

        model.addAttribute("user", user);
        return "dashboard";
    }

    @GetMapping("/dashboard/history")
    public String userHistory(HttpSession session, Model model) {
        User user = userService.getLoggedInUser(session);

        if (user == null) {
            return "redirect:/auth/login";
        }

        List<Transactions> history = transactionsService.getHistoryForUser(user);
        model.addAttribute("user", user);
        model.addAttribute("history", history);

        return "dashboard-history";
    }
}
