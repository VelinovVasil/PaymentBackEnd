package tech.bonda.PaymentBackEnd.service;

import tech.bonda.PaymentBackEnd.entities.account.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction saveTransaction(Transaction transaction);

    List<Transaction> getAllTransactions();

    void deleteTransaction(int id);
}
