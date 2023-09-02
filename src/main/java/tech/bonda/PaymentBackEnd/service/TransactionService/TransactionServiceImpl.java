package tech.bonda.PaymentBackEnd.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.bonda.PaymentBackEnd.entities.transaction.Transaction;
import tech.bonda.PaymentBackEnd.repository.TransactionRepository;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction getTransactionById(long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    @Override
    public Transaction updateTransaction(long id, Transaction transaction) {
        Transaction existingTransaction = transactionRepository.findById(id).orElse(null);
        existingTransaction.setAmount(transaction.getAmount());
        existingTransaction.setSenderIban(transaction.getSenderIban());
        existingTransaction.setReceiverIban(transaction.getReceiverIban());
        existingTransaction.setTimestamp(transaction.getTimestamp());
        return transactionRepository.save(existingTransaction);
    }

    @Override
    public void deleteTransaction(long id) {
        transactionRepository.deleteById(id);
    }


}
