package tech.bonda.PaymentBackEnd.service.CardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.bonda.PaymentBackEnd.entities.card.Card;
import tech.bonda.PaymentBackEnd.repository.CardRepository;
import tech.bonda.PaymentBackEnd.service.CardService.CardService;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    private CardRepository cardRepository;

    @Override
    public Card saveCard(Card card) {
        return cardRepository.save(card);
    }

    @Override
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    @Override
    public Card getCardById(long id) {
        return cardRepository.findById(id).orElse(null);
    }

    @Override
    public Card updateCard(long id, Card card) {
        Card existingCard = cardRepository.findById(id).orElse(null);
        existingCard.setCardholderName(card.getCardholderName());
        existingCard.setCardNumber(card.getCardNumber());
        existingCard.setExpirationDate(card.getExpirationDate());
        existingCard.setCvv(card.getCvv());
        existingCard.setPin(card.getPin());
        existingCard.setIban(card.getIban());
        return cardRepository.save(existingCard);
    }

    @Override
    public void deleteCard(long id) {
        cardRepository.deleteById(id);
    }

}
