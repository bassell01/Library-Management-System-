package com.example.lms.demo.services.factory;

public class TransactionFactory {
    public static TransactionTypeStrategy getStrategy(String type) {
        switch (type.toLowerCase()) {
            case "borrow":
                return new BorrowTransaction();
            case "purchase":
                return new PurchaseTransaction();
            default:
                throw new IllegalArgumentException("Invalid transaction type: " + type);
        }
    }
}