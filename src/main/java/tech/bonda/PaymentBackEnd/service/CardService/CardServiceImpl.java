package tech.bonda.PaymentBackEnd.service.CardService;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.bonda.PaymentBackEnd.entities.account.Account;
import tech.bonda.PaymentBackEnd.entities.card.Card;
import tech.bonda.PaymentBackEnd.repository.AccountRepository;
import tech.bonda.PaymentBackEnd.repository.CardRepository;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository, AccountRepository accountRepository) {
        this.cardRepository = cardRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Card createCardForAccount(Long accountId, Card card) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new EntityNotFoundException("Account not found for ID: " + accountId));

        card.setAccount(account);
        card.setIban("IBAN" + account.getId() + card.getId());
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
    public List<Card> getCardByAccount(long accountId) {
        return cardRepository.findByAccountId(accountId);
    }

    @Override
    public Card updateCard(long id, Card card) {
        Card existingCard = cardRepository.findById(id).orElse(null);
        if (existingCard != null)
        {
            existingCard.setCardholderName(card.getCardholderName());
            existingCard.setCardNumber(card.getCardNumber());
            existingCard.setExpirationDate(card.getExpirationDate());
            existingCard.setCvv(card.getCvv());
            existingCard.setPin(card.getPin());
            existingCard.setIban(card.getIban());
            return cardRepository.save(existingCard);
        }
        return null;
    }

    @Override
    public boolean deleteCard(long id) {
        if (cardRepository.existsById(id))
        {
            cardRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
