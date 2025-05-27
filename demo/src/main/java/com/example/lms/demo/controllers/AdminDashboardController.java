package com.example.lms.demo.controllers;

import com.example.lms.demo.models.Book;
import com.example.lms.demo.models.User;
import com.example.lms.demo.repositories.BookRepository;
import com.example.lms.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Controller
public class AdminDashboardController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @GetMapping("/Admin")
    public String showAdminDashboard() {
        return "redirect:/Admin.html";
    }

    @GetMapping("/api/books")
    @ResponseBody
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @PostMapping(value = "/api/books", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public Book addBook(
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("genre") String genre,
            @RequestParam("price") float price,
            @RequestParam("quantity") int quantity,
            @RequestParam("image") MultipartFile imageFile
    ) throws IOException {
        String filename = UUID.randomUUID() + "_" + StringUtils.cleanPath(imageFile.getOriginalFilename());
        Path uploadPath = Paths.get(UPLOAD_DIR);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(filename);
        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setPrice(price);
        book.setQuantity(quantity);
        book.setImageUrl("/uploads/" + filename); // This URL should work as long as the image is inside /static/uploads

        return bookRepository.save(book);
    }

    @DeleteMapping("/api/books/{id}")
    @ResponseBody
    public void deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
    }

    @PutMapping("/api/books/{id}")
    @ResponseBody
    public Book updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        Book book = bookRepository.findById(id).orElseThrow();
        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setGenre(updatedBook.getGenre());
        book.setPrice(updatedBook.getPrice());
        book.setQuantity(updatedBook.getQuantity());
        book.setImageUrl(updatedBook.getImageUrl());
        return bookRepository.save(book);
    }

    @GetMapping("/api/members")
    @ResponseBody
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PutMapping("/api/members/{id}")
    @ResponseBody
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = userRepository.findById(id).orElseThrow();
        user.setUsername(updatedUser.getUsername());
        user.setAddress(updatedUser.getAddress());
        return userRepository.save(user);
    }

    @DeleteMapping("/api/members/{id}")
    @ResponseBody
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
