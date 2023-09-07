package tech.bonda.PaymentBackEnd.entities.transaction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.bonda.PaymentBackEnd.entities.card.Card;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction implements Transactionable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_iban")
    private String senderIban;
    @Column(name = "receiver_iban")
    private String receiverIban;
    @Column(name = "amount")
    private double amount;
    @Column(name = "timestamp")
    private String timestamp;

    @ManyToOne
    @JoinColumn(name = "sender_card_id", referencedColumnName = "id")
    @JsonBackReference
    private Card senderCard;

    @ManyToOne
    @JoinColumn(name = "receiver_card_id", referencedColumnName = "id")
    @JsonBackReference
    private Card receiverCard;
}
