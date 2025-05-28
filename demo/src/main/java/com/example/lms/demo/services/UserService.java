package com.example.lms.demo.services;

import org.springframework.stereotype.Service;

import com.example.lms.demo.models.User;

import jakarta.servlet.http.HttpSession;

@Service
public class UserService {

    // جلب المستخدم الحالي من الجلسة (session)
    public User getLoggedInUser(HttpSession session) {
        Object userObj = session.getAttribute("user");
        if (userObj instanceof User) {
            return (User) userObj;
        }
        return null;
    }
}
