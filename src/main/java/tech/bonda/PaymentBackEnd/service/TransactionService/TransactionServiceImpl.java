package tech.bonda.PaymentBackEnd.service.TransactionService;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.bonda.PaymentBackEnd.entities.transaction.Transaction;
import tech.bonda.PaymentBackEnd.repository.TransactionRepository;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

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
        return transactionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Transaction not found for ID: " + id));
    }

    @Override
    public Transaction updateTransaction(long id, Transaction transaction) {
        Transaction existingTransaction = transactionRepository.findById(id).orElse(null);
        if (existingTransaction != null)
        {
            existingTransaction.setAmount(transaction.getAmount());
            existingTransaction.setSenderIban(transaction.getSenderIban());
            existingTransaction.setReceiverIban(transaction.getReceiverIban());
            existingTransaction.setTimestamp(transaction.getTimestamp());
            return transactionRepository.save(existingTransaction);
        }
        return null;
    }

    @Override
    public boolean deleteTransaction(long id) {
        if (transactionRepository.existsById(id))
        {
            transactionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Transaction> getTransactionsByCardId(long cardId) {
        return transactionRepository.findTransactionsBySenderOrReceiverCardId(cardId);
    }
}

