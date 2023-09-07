package tech.bonda.PaymentBackEnd.service.TransactionService;

import tech.bonda.PaymentBackEnd.entities.transaction.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction saveTransaction(Transaction transaction);

    List<Transaction> getAllTransactions();

    boolean deleteTransaction(long id);

    Transaction updateTransaction(long id, Transaction transaction);

    Transaction getTransactionById(long id);

    List<Transaction> getTransactionsByCardId(long cardId);
}
