package tech.bonda.PaymentBackEnd.service.CardService;

import tech.bonda.PaymentBackEnd.entities.card.Card;

import java.util.List;

public interface CardService {
    Card saveCard(Card card);

    List<Card> getAllCards();

    Card getCardById(long id);

    Card updateCard(long id, Card card);

    void deleteCard(long id);

}
