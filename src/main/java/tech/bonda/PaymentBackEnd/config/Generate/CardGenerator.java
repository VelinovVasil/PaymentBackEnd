package tech.bonda.PaymentBackEnd.config.Generate;

import com.github.javafaker.Faker;
import tech.bonda.PaymentBackEnd.entities.account.Account;
import tech.bonda.PaymentBackEnd.entities.card.Card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class CardGenerator {
    private final Faker faker = new Faker();
    private final Faker fakerBg = new Faker(Locale.forLanguageTag("bg"));
    private List<Long> availableAccountIds = getAvailableAccountIds();
    private final int count = availableAccountIds.size();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of cards to generate: ");
        int n = scanner.nextInt();
        for (int i = 0; i < n; i++)
        {
            CardGenerator cardGenerator = new CardGenerator();
            Long accountId = cardGenerator.getRandomAccountId();
            Account account = cardGenerator.getAccountById(accountId);
            Card card = cardGenerator.generateRandomCard(account);
            boolean saved = cardGenerator.saveInDatabase(accountId, card);
            cardGenerator.showCardDetails(accountId, card, saved);
        }
    }

    private Card generateRandomCard(Account account) {
        Card card = new Card();
        card.setBalance(0);
        card.setCardholderName(account.getName());
        card.setCardNumber(generateRandomCardNumber());
        card.setCvv(generateRandomCVV());
        card.setExpirationDate(generateRandomExpirationDate());
        card.setIban(generateRandomIBAN());
        card.setPin(generateRandomPIN());
        return card;
    }

    private String generateRandomCardNumber() {
        String cardNumber = faker.finance().creditCard().replace("-", " ");
        return cardNumber;
    }

    private String generateRandomCVV() {
        int cvv = ThreadLocalRandom.current().nextInt(100, 1000);
        return String.valueOf(cvv);
    }

    private String generateRandomExpirationDate() {
        int randomYear = ThreadLocalRandom.current().nextInt(2, 21);
        int randomMonth = ThreadLocalRandom.current().nextInt(1, 13);
        return String.format("%02d/%02d", randomMonth, randomYear);
    }

    private String generateRandomIBAN() {
        return fakerBg.finance().iban();
    }

    private String generateRandomPIN() {
        int pin = ThreadLocalRandom.current().nextInt(1000, 10000);
        return String.valueOf(pin);
    }

    public List<Long> getAvailableAccountIds() {
        List<Long> accountIds = new ArrayList<>();
        try (Connection connection = Connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM account"); ResultSet resultSet = preparedStatement.executeQuery())
        {

            while (resultSet.next())
            {
                long accountId = resultSet.getLong("id");
                accountIds.add(accountId);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return accountIds;
    }

    private boolean saveInDatabase(Long accountId, Card card) {
        try (Connection connection = Connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO card (balance, cardholder_name, iban, card_number, cvv, expiration_date, pin, account_id) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"))
        {

            preparedStatement.setDouble(1, card.getBalance());
            preparedStatement.setString(2, card.getCardholderName());
            preparedStatement.setString(3, card.getIban());
            preparedStatement.setString(4, card.getCardNumber());
            preparedStatement.setString(5, card.getCvv());
            preparedStatement.setString(6, card.getExpirationDate());
            preparedStatement.setString(7, card.getPin());
            preparedStatement.setLong(8, accountId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    private Account getAccountById(Long accountId) {
        Account account = null;
        try (Connection connection = Connector.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM account WHERE id = ?"))
        {

            preparedStatement.setLong(1, accountId);
            try (ResultSet resultSet = preparedStatement.executeQuery())
            {
                if (resultSet.next())
                {
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String username = resultSet.getString("username");
                    String phoneNumber = resultSet.getString("phone_number");
                    String egn = resultSet.getString("egn");
                    String dateOfCreation = resultSet.getString("date_of_creation");

                    account = new Account();
                    account.setName(name);
                    account.setEmail(email);
                    account.setUsername(username);
                    account.setPhoneNumber(phoneNumber);
                    account.setEgn(egn);
                    account.setDateOfCreation(dateOfCreation);
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return account;
    }

    private long getRandomAccountId() {
        if (availableAccountIds.isEmpty())
        {
            availableAccountIds = getAvailableAccountIds();
        }
        int randomIndex = ThreadLocalRandom.current().nextInt(count);
        long randomAccountId = availableAccountIds.get(randomIndex);
        availableAccountIds.remove(randomAccountId);
        return randomAccountId;
    }

    private void showCardDetails(Long accountId, Card card, boolean isSaved) {
        System.out.println("Generated Card Details:");
        System.out.println("Balance: " + card.getBalance());
        System.out.println("Cardholder Name: " + card.getCardholderName());
        System.out.println("Card Number: " + card.getCardNumber());
        System.out.println("Expiration Date: " + card.getExpirationDate());
        System.out.println("IBAN: " + card.getIban());
        System.out.println("PIN: " + card.getPin());
        System.out.println("CVV: " + card.getCvv());
        System.out.println("Account ID: " + accountId);
        System.out.println("Saved in database: " + isSaved);
        System.out.println("---------------------------------");
    }
}
