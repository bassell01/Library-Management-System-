package com.example.lms.demo.services.factory;

import com.example.lms.demo.models.Book;
import com.example.lms.demo.models.Transactions;
import com.example.lms.demo.models.User;

public interface TransactionTypeStrategy {
    Transactions createTransaction(User user, Book book);
}
