package com.example.lms.demo.controllers;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.lms.demo.models.Book;
import com.example.lms.demo.models.Payment;
import com.example.lms.demo.models.Transactions;
import com.example.lms.demo.models.User;
import com.example.lms.demo.repositories.BookRepository;
import com.example.lms.demo.repositories.PaymentRepository;
import com.example.lms.demo.services.TransactionsService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TransactionsService transactionsService;

    @Autowired
    private PaymentRepository paymentRepository;


    @GetMapping("/{bookId}")
    public String checkoutPage(@PathVariable Long bookId, @RequestParam String type, HttpSession session, org.springframework.ui.Model model) {
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        User user = (User) session.getAttribute("loggedInUser");

        if (bookOpt.isEmpty() || user == null) {
            return "redirect:/auth/login";
        }

        model.addAttribute("book", bookOpt.get());
        model.addAttribute("type", type);
        model.addAttribute("user", user);

        return "checkout"; 
    }

    @PostMapping("/complete")
    public String completeCheckout(@RequestParam Long bookId, @RequestParam String type, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        Optional<Book> bookOpt = bookRepository.findById(bookId);

        if (user == null || bookOpt.isEmpty()) {
            return "redirect:/auth/login";
        }

        Book book = bookOpt.get();

        
        Transactions transaction = transactionsService.createTransaction(user, book, type);

        
        if ("purchase".equalsIgnoreCase(type)) {
            Payment payment = new Payment();
            payment.setTransaction(transaction);
            payment.setAmount(book.getPrice());
            payment.setPaymentMethod("Credit Card"); 
            payment.setPaymentDate(LocalDate.now());

            paymentRepository.save(payment);
        }

        return "redirect:/dashboard/history";
    }



}
