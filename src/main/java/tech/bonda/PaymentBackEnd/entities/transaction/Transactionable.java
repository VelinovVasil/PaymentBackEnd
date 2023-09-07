package tech.bonda.PaymentBackEnd.entities.transaction;

public interface Transactionable {

    void setSenderIban(String senderIban);

    void setReceiverIban(String receiverIban);

    void setAmount(double amount);

    void setTimestamp(String timestamp);
}
