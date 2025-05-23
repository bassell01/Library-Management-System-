package com.example.lms.demo.repositories;

import com.example.lms.demo.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {}
