package com.example.lms.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/about")
    public String about() {
        return "About";  // يرجع ملف About.html من templates
    }

    @GetMapping("/contact")
    public String contact() {
        return "Contact"; // يرجع ملف Contact.html من templates
    }
}
