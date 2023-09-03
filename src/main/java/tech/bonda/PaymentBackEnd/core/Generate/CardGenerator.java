package tech.bonda.PaymentBackEnd.core.Generate;

import com.github.javafaker.Faker;
import tech.bonda.PaymentBackEnd.entities.card.Card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CardGenerator {
    private final Faker faker = new Faker();
    private final Faker fakerBg = new Faker(Locale.of("bg"));

    public static void main(String[] args) {
        CardGenerator cardGenerator = new CardGenerator();

        Card card = cardGenerator.generateRandomCard();
        System.out.println("Generated Card Details:");
        System.out.println("Cardholder Name: " + card.getCardholderName());
        System.out.println("Card Number: " + card.getCardNumber());
        System.out.println("Expiration Date: " + card.getExpirationDate());
        System.out.println("IBAN: " + card.getIban());
        System.out.println("PIN: " + card.getPin());
        System.out.println("CVV: " + card.getCvv());
        //cardGenerator.saveInDatabase(card);
    }

    private void saveInDatabase(Card card) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = Connector.getConnection();
            String insertSql = "INSERT INTO card (card_number, cardholder_name, cvv, expiration_date, iban, pin, account_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setString(1, card.getCardNumber());
            preparedStatement.setString(2, card.getCardholderName());
            preparedStatement.setString(3, card.getCvv());
            preparedStatement.setString(4, card.getExpirationDate());
            preparedStatement.setString(5, card.getIban());
            preparedStatement.setString(6, card.getPin());
            // preparedStatement.setString(7, card.getAccountId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0)
            {
                System.out.println("Card saved to the database.");
            }
            else
            {
                System.out.println("Failed to save card to the database.");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if (connection != null)
                {
                    connection.close();
                }
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    private Card generateRandomCard() {

        Card card = new Card();
        card.setBalance(0);
        card.setCardNumber(generateRandomCardNumber());
        card.setCardholderName(faker.name().fullName());
        card.setCvv(generateRandomCVV());
        card.setExpirationDate(generateRandomExpirationDate());
        card.setIban(generateRandomIBAN());
        card.setPin(generateRandomPIN());
        return card;
    }

    private String generateRandomCardNumber() {
        String cardNumber = faker.finance().creditCard();
        return cardNumber.replace("-", " ");
    }

    private String generateRandomCVV() {
        int cvv = faker.number().numberBetween(100, 999);
        return String.valueOf(cvv);
    }

    private String generateRandomExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        Random random = new Random();
        int randomYear = random.nextInt(19) + 2;
        calendar.add(Calendar.YEAR, randomYear);
        int randomMonth = random.nextInt(12) + 1;
        calendar.set(Calendar.MONTH, randomMonth - 1);
        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yy");
        return dateFormat.format(date);
    }

    private String generateRandomIBAN() {
        // Implement logic to generate a random IBAN
        // Example: "BG80 BNBG 9661 1020 3456 78"
        return "BG80 BNBG 9661 1020 3456 78";
    }

    private String generateRandomPIN() {
        // Implement logic to generate a random PIN
        // Example: "1234"
        return "1234";
    }

    public List<Long> getAvailableAccountIds() {
        List<Long> accountIds = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try
        {
            connection = Connector.getConnection();
            String selectSql = "SELECT id FROM account";
            preparedStatement = connection.prepareStatement(selectSql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                long accountId = resultSet.getLong("id");
                accountIds.add(accountId);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if (connection != null)
                {
                    connection.close();
                }
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return accountIds;
    }
}

