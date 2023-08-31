package tech.bonda.PaymentBackEnd.entities.card;

public interface Cardable {

    void setCardholderName(String cardHolderName);
    void setIban(String iban);
    void setCardNumber(String cardNumber);
    void setExpirationDate(String expirationDate);
    void setCvv(String cvv);
    void setPin(String pin);
}
