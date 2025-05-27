package com.example.lms.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.lms.demo.models.Transactions;
import com.example.lms.demo.models.User;
import com.example.lms.demo.repositories.TransactionsRepository;
@Service
public class TransactionsService {
    @Autowired
    private TransactionsRepository transactionsRepository;

    public List<Transactions> getHistoryForUser(User user) {
        return transactionsRepository.findByUser(user);
    }
}
