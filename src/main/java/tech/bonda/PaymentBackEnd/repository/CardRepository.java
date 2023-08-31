package tech.bonda.PaymentBackEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.bonda.PaymentBackEnd.entities.card.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
}
