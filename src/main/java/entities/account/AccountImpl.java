package entities.account;

import java.util.IllegalFormatWidthException;

public class AccountImpl implements Account {

    private static String iban;
    private static String egn;
    private static double interest;
    private double balance;
    private String date;

    public AccountImpl(String iban, String egn, double interest, double balance, String date) {
        this.setIban(iban);
        this.setEgn(egn);
        this.setInterest(interest);
        this.setBalance(balance);
        this.setDate(date);
    }

    @Override
    public String getIban() {
        return this.iban;
    }

    @Override
    public String getEgn() {
        return this.egn;
    }

    @Override
    public double getBalance() {
        return this.balance;
    }

    @Override
    public double getInterest() {
        return this.interest;
    }

    @Override
    public String getDate() {
        return this.date;
    }

    @Override
    public void deposit(double amount) {
        this.balance += amount;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > this.balance) {
            throw new IllegalArgumentException("Insufficient funds for this operation");
        }

        this.balance -= amount;
    }

    @Override
    public void updateDate(String newDate) {
        this.setDate(newDate);
    }

    private void setBalance(double balance) {

        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be below zero");
        }

        this.balance = balance;
    }

    private void setDate(String date) {

        // TODO: Implement logic to check the validity of the date
        // My recommendation is regEx

        this.date = date;
    }

    private void setIban(String iban) {

        if (iban.length() > 34 ) {
            throw new IllegalArgumentException("Incorrect iban format");
        }

        iban = iban.toUpperCase();

        this.iban = iban;
    }

    private void setEgn(String egn) {

        for (int i = 0; i < egn.length(); i++) {

            if (egn.charAt(i) >= '0' && egn.charAt(i) <= '9') {

                continue;

            } else {

                throw new IllegalArgumentException("Egn should contain digits only");

            }

        }

        this.egn = egn;
    }

    private void setInterest(double interest) {

        if (interest <= 0) {
            throw new IllegalArgumentException("Interest cannot be below or equal to zero");
        }

        this.interest = interest;
    }
}
