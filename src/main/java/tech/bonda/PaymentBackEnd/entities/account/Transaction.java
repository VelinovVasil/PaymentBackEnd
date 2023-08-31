package tech.bonda.PaymentBackEnd.entities.account;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String senderIban;
    private String receiverIban;
    private double amount;
    private String timestamp;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Transaction() {
    }

    public Transaction(String senderIban, String receiverIban, double amount, String timestamp) {
        this.setSenderIban(senderIban);
        this.setReceiverIban(receiverIban);
        this.setAmount(amount);
        this.setTimestamp(timestamp);
    }

    public void setSenderIban(String senderIban) {
        this.senderIban = senderIban;
    }

    public void setReceiverIban(String receiverIban) {
        this.receiverIban = receiverIban;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


}
