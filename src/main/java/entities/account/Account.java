package entities.account;

public interface Account {

    String getIban();
    String getEgn();
    double getBalance();
    double getInterest();
    String getDate();
    void deposit(double amount);
    void withdraw(double amount);
    void updateDate(String newDate);


}
