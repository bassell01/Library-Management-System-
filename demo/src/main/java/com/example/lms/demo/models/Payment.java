package com.example.lms.demo.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @OneToOne
    @JoinColumn(name = "transaction_id")
    private Transactions transaction;

    private float amount;
    private String paymentMethod; // Card, Cash, etc.
    private LocalDate paymentDate;
}
