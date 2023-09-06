package tech.bonda.PaymentBackEnd.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.bonda.PaymentBackEnd.entities.card.Card;
import tech.bonda.PaymentBackEnd.service.CardService.CardService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/card")
public class CardController {
    @Autowired
    private CardService cardService;

    @PostMapping("/add/{accountId}")
    public ResponseEntity<Card> createCardForAccount(@PathVariable Long accountId, @RequestBody Card card) {
        // Call the service layer to create and associate the card with the account
        Card createdCard = cardService.createCardForAccount(accountId, card);
        return ResponseEntity.ok(createdCard);
    }

    @GetMapping("/getAll")
    public List<Card> getAll() {
        return cardService.getAllCards();
    }

    @GetMapping("/get/{id}")
    public Card get(@PathVariable long id) {
        return cardService.getCardById(id);
    }

    @PostMapping("/update/{id}")
    public Card update(@PathVariable long id, @RequestBody Card card) {
        return cardService.updateCard(id, card);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable long id) {
        cardService.deleteCard(id);
    }


}
