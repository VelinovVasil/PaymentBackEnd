package tech.bonda.PaymentBackEnd.service.CardService;

import tech.bonda.PaymentBackEnd.entities.card.Card;

import java.util.List;

public interface CardService {
    Card createCardForAccount(Long accountId, Card card);

    List<Card> getAllCards();

    Card getCardById(long id);

    List<Card> getCardByAccount(long accountId);

    Card updateCard(long id, Card card);

    boolean deleteCard(long id);

}
