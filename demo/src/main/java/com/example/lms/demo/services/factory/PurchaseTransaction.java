package com.example.lms.demo.services.factory;

import java.time.LocalDate;

import com.example.lms.demo.models.Book;
import com.example.lms.demo.models.Transactions;
import com.example.lms.demo.models.User;

public class PurchaseTransaction implements TransactionTypeStrategy{
    
    @Override
    public Transactions createTransaction(User user, Book book) {
        Transactions tx = new Transactions();
        tx.setUser(user);
        tx.setBook(book);
        tx.setType("Purchase");
        tx.setDateIssued(LocalDate.now());
        tx.setDueDate(null); // no due date for purchase
        tx.setPaymentStatus("Paid");
        return tx;
    }
}
