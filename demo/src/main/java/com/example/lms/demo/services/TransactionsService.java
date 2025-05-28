package com.example.lms.demo.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.lms.demo.models.Book;
import com.example.lms.demo.models.Transactions;
import com.example.lms.demo.models.User;
import com.example.lms.demo.repositories.TransactionsRepository;

@Service
public class TransactionsService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    // دالة لإرجاع تاريخ المعاملات الخاصة بالمستخدم
    public List<Transactions> getHistoryForUser(User user) {
        return transactionsRepository.findByUser(user);
    }

    // تنفيذ عملية إنشاء معاملة جديدة وحفظها
    public Transactions createTransaction(User user, Book book, String type) {
        Transactions transaction = new Transactions();
        transaction.setUser(user);
        transaction.setBook(book);
        transaction.setType(type);
        transaction.setDateIssued(LocalDate.now());

        if ("borrow".equalsIgnoreCase(type)) {
            transaction.setDueDate(LocalDate.now().plusWeeks(2));  // مثال: الاستعارة لمدة أسبوعين
            transaction.setPaymentStatus("Pending");
        } else if ("purchase".equalsIgnoreCase(type)) {
            transaction.setDueDate(null); // لا يوجد موعد استحقاق للشراء
            transaction.setPaymentStatus("Paid"); // مفترض دفع مباشر
        } else {
            transaction.setPaymentStatus("Pending");
        }

        return transactionsRepository.save(transaction);
    }
}
