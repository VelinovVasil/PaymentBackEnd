package tech.bonda.PaymentBackEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.bonda.PaymentBackEnd.entities.account.Card;

public interface CardRepository extends JpaRepository<Card, Long> {
}
