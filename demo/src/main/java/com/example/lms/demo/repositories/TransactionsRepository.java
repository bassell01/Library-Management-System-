package com.example.lms.demo.repositories;
import com.example.lms.demo.models.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsRepository extends JpaRepository<Transactions, Long> {}