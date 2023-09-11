package tech.bonda.PaymentBackEnd.entities.card;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.bonda.PaymentBackEnd.entities.account.Account;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Card implements Cardable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "balance", columnDefinition = "DECIMAL(10, 2)")
    private double balance;

    @Column(name = "cardholder_name", columnDefinition = "VARCHAR(255)")
    private String cardholderName;

    @Column(name = "iban", unique = true, columnDefinition = "VARCHAR(255)")
    private String iban;

    @Column(name = "card_number", unique = true, columnDefinition = "VARCHAR(255)")
    private String cardNumber;

    @Column(name = "expiration_date", columnDefinition = "VARCHAR(255)")
    private String expirationDate;

    @Column(name = "cvv", columnDefinition = "VARCHAR(255)")
    private String cvv;

    @Column(name = "pin", columnDefinition = "VARCHAR(255)")
    private String pin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    @JsonBackReference
    private Account account;
}
