package tech.bonda.PaymentBackEnd.service.CardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.bonda.PaymentBackEnd.entities.account.Account;
import tech.bonda.PaymentBackEnd.entities.card.Card;
import tech.bonda.PaymentBackEnd.repository.AccountRepository;
import tech.bonda.PaymentBackEnd.repository.CardRepository;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Card createCardForAccount(Long accountId, Card card) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Cannot find account by ID " + accountId));

        card.setAccount(account);

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
