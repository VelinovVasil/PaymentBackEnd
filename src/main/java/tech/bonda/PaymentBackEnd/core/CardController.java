package tech.bonda.PaymentBackEnd.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.bonda.PaymentBackEnd.entities.card.Card;
import tech.bonda.PaymentBackEnd.service.CardService.CardService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

@RequestMapping("/api/v1/card")
public class CardController {
    @Autowired
    private CardService cardService;

    @PostMapping("/register")
    public Card create(@RequestBody Card card) {
        return cardService.saveCard((Card) card);
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
