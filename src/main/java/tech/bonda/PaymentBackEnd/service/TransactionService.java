package tech.bonda.PaymentBackEnd.service;

import tech.bonda.PaymentBackEnd.entities.transaction.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction saveTransaction(Transaction transaction);

    List<Transaction> getAllTransactions();

    void deleteTransaction(int id);
}
