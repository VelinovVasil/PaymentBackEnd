package tech.bonda.PaymentBackEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tech.bonda.PaymentBackEnd.entities.card.Card;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findByAccountId(long accountId);

    @Query("SELECT MAX(c.id) FROM Card c")
    int getLatestId();

}
