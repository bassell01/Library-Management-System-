package com.example.lms.demo.controllers;

import com.example.lms.demo.models.Book;
import com.example.lms.demo.models.User;
import com.example.lms.demo.repositories.BookRepository;
import com.example.lms.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminDashboardController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    // Serve admin dashboard page (Static HTML located in /static folder)
    @GetMapping("/Admin")
    public String showAdminDashboard() {
        return "redirect:/Admin.html";
    }

    // Get all books
    @GetMapping("/api/books")
    @ResponseBody
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Add a new book
    @PostMapping("/api/books")
    @ResponseBody
    public Book addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    // Delete a book
    @DeleteMapping("/api/books/{id}")
    @ResponseBody
    public void deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
    }

    // Update a book
    @PutMapping("/api/books/{id}")
    @ResponseBody
    public Book updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        Book book = bookRepository.findById(id).orElseThrow();
        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setGenre(updatedBook.getGenre());
        book.setPrice(updatedBook.getPrice());
        return bookRepository.save(book);
    }

    // Get all users (members)
    @GetMapping("/api/members")
    @ResponseBody
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Update a member
    @PutMapping("/api/members/{id}")
    @ResponseBody
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = userRepository.findById(id).orElseThrow();
        user.setUsername(updatedUser.getUsername());
        user.setAddress(updatedUser.getAddress());
        return userRepository.save(user);
    }

    // Delete a member
    @DeleteMapping("/api/members/{id}")
    @ResponseBody
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
