package com.example.lms.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.lms.demo.models.Book;
import com.example.lms.demo.models.User;
import com.example.lms.demo.repositories.BookRepository;
import com.example.lms.demo.services.TransactionsService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TransactionsService transactionsService;

    @GetMapping
    public String listBooks(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "books-list";
    }
    
    @GetMapping("/{id}")
    public String bookDetails(@PathVariable Long id, Model model) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        if (bookOpt.isPresent()) {
            model.addAttribute("book", bookOpt.get());
            return "book-detail";
        } else {
            return "error";
        }
    }

    @PostMapping("/{id}/transaction")
    public String createTransaction(@PathVariable Long id,
                                    @RequestParam String type,
                                    HttpSession session) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        User user = (User) session.getAttribute("user");  // ثابت الاسم "user"

        if (bookOpt.isPresent() && user != null) {
            transactionsService.createTransaction(user, bookOpt.get(), type.toLowerCase());
            return "redirect:/dashboard/history";
        }
        return "redirect:/auth/login";
    }
}
