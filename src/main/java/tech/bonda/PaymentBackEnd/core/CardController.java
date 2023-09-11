package tech.bonda.PaymentBackEnd.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.bonda.PaymentBackEnd.entities.card.Card;
import tech.bonda.PaymentBackEnd.service.CardService.CardService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/card")
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/add/{accountId}")
    public ResponseEntity<Card> createCardForAccount(@PathVariable Long accountId, @RequestBody Card card) {
        Card createdCard = cardService.createCardForAccount(accountId, card);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCard);
    }

    @GetMapping("/")
    public ResponseEntity<List<Card>> getAll() {
        List<Card> cards = cardService.getAllCards();
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> get(@PathVariable long id) {
        Card card = cardService.getCardById(id);
        if (card != null)
        {
            return ResponseEntity.ok(card);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAllByAccount/{accountId}")
    public ResponseEntity<List<Card>> getAllByAccount(@PathVariable long accountId) {
        List<Card> cards = cardService.getCardByAccount(accountId);
        if (cards != null)
        {
            return ResponseEntity.ok(cards);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Card> update(@PathVariable long id, @RequestBody Card card) {
        Card updatedCard = cardService.updateCard(id, card);
        if (updatedCard != null)
        {
            return ResponseEntity.ok(updatedCard);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        boolean deleted = cardService.deleteCard(id);
        if (deleted)
        {
            return ResponseEntity.noContent().build();
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }
}
