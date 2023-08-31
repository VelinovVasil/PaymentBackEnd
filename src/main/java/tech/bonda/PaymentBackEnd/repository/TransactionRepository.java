package tech.bonda.PaymentBackEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.bonda.PaymentBackEnd.entities.account.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}