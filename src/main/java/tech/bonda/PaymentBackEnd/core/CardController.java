package tech.bonda.PaymentBackEnd.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.bonda.PaymentBackEnd.entities.card.Card;
import tech.bonda.PaymentBackEnd.service.CardService.CardService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/card")
public class CardController implements Controller{
    @Autowired
    private CardService cardService;

    @Override
    @PostMapping("/create")
    public Card create(@RequestBody Object object) {
        return cardService.saveCard((Card) object);
    }

    @Override
    @GetMapping("/getAll")
    public List<Object> getAll() {
        return Collections.singletonList(Collections.singleton(cardService.getAllCards()));
    }

    @Override
    @GetMapping("/get/{id}")
    public Card get(@PathVariable long id) {
        return cardService.getCardById(id);
    }

    @Override
    @PostMapping("/update/{id}")
    public Card update(@PathVariable long id, @RequestBody Object object) {
        return cardService.updateCard(id, (Card) object);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable long id) {
        cardService.deleteCard(id);
    }
}
