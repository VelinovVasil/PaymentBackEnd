package tech.bonda.PaymentBackEnd.service;

import tech.bonda.PaymentBackEnd.entities.account.Card;

import java.util.List;

public interface CardService {
    Card saveCard(Card card);

    List<Card> getAllCards();

    void deleteCard(long id);

}
