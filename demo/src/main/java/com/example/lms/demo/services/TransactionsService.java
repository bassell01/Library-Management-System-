package com.example.lms.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.lms.demo.models.Book;
import com.example.lms.demo.models.Transactions;
import com.example.lms.demo.models.User;
import com.example.lms.demo.repositories.TransactionsRepository;
import com.example.lms.demo.services.factory.TransactionFactory;
import com.example.lms.demo.services.factory.TransactionTypeStrategy;
@Service
public class TransactionsService {
    @Autowired
    private TransactionsRepository transactionsRepository;

    public List<Transactions> getHistoryForUser(User user) {
        return transactionsRepository.findByUser(user);
    }

    public Transactions createTransaction(User user, Book book, String type) {
        TransactionTypeStrategy strategy = TransactionFactory.getStrategy(type);
        Transactions transaction = strategy.createTransaction(user, book);
        return transactionsRepository.save(transaction);
    }
}
