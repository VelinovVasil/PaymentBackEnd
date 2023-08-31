package tech.bonda.PaymentBackEnd.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.bonda.PaymentBackEnd.entities.card.Card;
import tech.bonda.PaymentBackEnd.service.CardService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/card")
public class CardController {
    @Autowired
    private CardService cardService;

    @PostMapping("/add")
    public String add(@RequestBody Card card) {
        cardService.saveCard(card);
        return "New card is added";
    }

    @GetMapping("/getAll")
    public List<Card> getAll() {
        return cardService.getAllCards();
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        cardService.deleteCard(id);
        return "Card is deleted with id " + id;
    }
}
