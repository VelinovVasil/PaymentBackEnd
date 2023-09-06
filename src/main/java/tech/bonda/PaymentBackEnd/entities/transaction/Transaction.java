package tech.bonda.PaymentBackEnd.entities.transaction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import tech.bonda.PaymentBackEnd.entities.card.Card;

@Getter
@Entity
public class Transaction implements Transactionable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String senderIban;
    private String receiverIban;
    private double amount;
    private String timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    @JsonBackReference
    private Card card;


    public Transaction() {
    }

    @Override
    public void setSenderIban(String senderIban) {
        this.senderIban = senderIban;
    }

    @Override
    public void setReceiverIban(String receiverIban) {
        this.receiverIban = receiverIban;
    }

    @Override
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


}
