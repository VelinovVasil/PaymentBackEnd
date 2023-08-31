package tech.bonda.PaymentBackEnd.entities.transaction;

import jakarta.persistence.*;
import lombok.Getter;
import tech.bonda.PaymentBackEnd.entities.account.Account;

@Getter
@Entity
public class Transaction implements Transactionable{
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
