package tech.bonda.PaymentBackEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.bonda.PaymentBackEnd.entities.card.Card;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findByAccountId(long accountId);

    String findTopByOrderByIdDesc();
}
