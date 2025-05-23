package com.example.lms.demo.repositories;
import com.example.lms.demo.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {}