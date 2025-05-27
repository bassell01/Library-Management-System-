package com.example.lms.demo.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lms.demo.models.Transactions;
import com.example.lms.demo.models.User;

public interface TransactionsRepository extends JpaRepository<Transactions, Long> {
    List<Transactions> findByUser(User user);
}