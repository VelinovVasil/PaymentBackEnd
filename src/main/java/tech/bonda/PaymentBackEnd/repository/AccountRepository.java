package tech.bonda.PaymentBackEnd.repository;

import tech.bonda.PaymentBackEnd.entities.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
}
