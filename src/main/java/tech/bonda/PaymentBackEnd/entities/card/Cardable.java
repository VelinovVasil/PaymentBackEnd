package tech.bonda.PaymentBackEnd.entities.card;

import tech.bonda.PaymentBackEnd.entities.account.Account;

public interface Cardable {

    void setBalance(double balance);
    void setCardholderName(String cardHolderName);
    void setIban(String iban);
    void setCardNumber(String cardNumber);
    void setExpirationDate(String expirationDate);
    void setCvv(String cvv);
    void setPin(String pin);

    void setAccount(Account account);

}
