package com.example.lms.demo.controllers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.lms.demo.models.Book;
import com.example.lms.demo.repositories.BookRepository;

@Controller
public class HomeController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/")
    public String home(Model model) {
        List<Book> allBooks = bookRepository.findAll();

        model.addAttribute("booksCount", allBooks.size());

        if (!allBooks.isEmpty()) {
            Collections.shuffle(allBooks);
            List<Book> randomBooks = allBooks.stream().limit(6).collect(Collectors.toList());
            model.addAttribute("randomBooks", randomBooks);
        } else {
            model.addAttribute("randomBooks", Collections.emptyList());
        }

        return "home";  // home.html
    }
}
