package tech.bonda.PaymentBackEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.bonda.PaymentBackEnd.entities.account.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
