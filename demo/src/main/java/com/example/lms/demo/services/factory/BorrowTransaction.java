package com.example.lms.demo.services.factory;

import java.time.LocalDate;

import com.example.lms.demo.models.Book;
import com.example.lms.demo.models.Transactions;
import com.example.lms.demo.models.User;

public class BorrowTransaction implements TransactionTypeStrategy  {
    @Override
    public Transactions createTransaction(User user, Book book) {
        Transactions tx = new Transactions();
        tx.setUser(user);
        tx.setBook(book);
        tx.setType("Borrow");
        tx.setDateIssued(LocalDate.now());
        tx.setDueDate(LocalDate.now().plusDays(14)); // 2 weeks due date
        tx.setPaymentStatus("Pending");
        return tx;
    }
}
