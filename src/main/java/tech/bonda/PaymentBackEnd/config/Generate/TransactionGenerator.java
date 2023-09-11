package tech.bonda.PaymentBackEnd.config.Generate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import tech.bonda.PaymentBackEnd.entities.card.Card;
import tech.bonda.PaymentBackEnd.entities.transaction.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

public class TransactionGenerator implements AutoCloseable {
    private static final Random random = new Random();
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private final HikariDataSource dataSource;

    public TransactionGenerator() {
        // Configure HikariCP for database connection pooling
        String jdbcUrl = "jdbc:mysql://link.rdb.superhosting.bg:3306/bondatec_bonda";
        String username = "bondatec_bonda";
        String password = "fMBX6RV4SAgGtiB";
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        dataSource = new HikariDataSource(config);
    }


    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of transactions to generate: ");
        int n = scanner.nextInt();

        for (int i = 0; i < n; i++)
        {
            long senderCardId = getRandomCardId();
            long receiverCardId = getRandomCardId();
            Transaction transaction = generateRandomTransaction(senderCardId, receiverCardId);
            saveInDatabase(senderCardId, receiverCardId, transaction);
        }
        System.out.println("Transactions generated successfully!");
    }

    public Transaction generateRandomTransaction(long receiverCardId, long senderCardId) {
        double amount = generateRandomAmount(0, 1000);
        String timestamp = generateTimestamp();
        Card senderCard = getCardById(senderCardId);
        Card receiverCard = getCardById(receiverCardId);
        Transaction transaction = new Transaction();
        transaction.setSenderIban(senderCard.getIban());
        transaction.setReceiverIban(receiverCard.getIban());
        transaction.setAmount(amount);
        transaction.setTimestamp(timestamp);
        return transaction;
    }

    private int generateRandomAmount(double min, double max) {
        return (int) (min + (max - min) * random.nextDouble());
    }

    private String generateTimestamp() {
        Instant currentTimestamp = Instant.now();
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = currentTimestamp.atZone(zoneId);
        return TIMESTAMP_FORMATTER.format(zonedDateTime);
    }

    private long getRandomCardId() {
        try (Connection connection = dataSource.getConnection())
        {
            String selectSql = "SELECT id FROM card ORDER BY RAND() LIMIT 1";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql); ResultSet resultSet = preparedStatement.executeQuery())
            {
                if (resultSet.next())
                {
                    return resultSet.getLong("id");
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        throw new RuntimeException("Failed to retrieve a random card ID.");
    }

    private Card getCardById(long id) {
        try (Connection connection = dataSource.getConnection())
        {
            String selectSql = "SELECT * FROM card WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql))
            {
                preparedStatement.setLong(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery())
                {
                    if (resultSet.next())
                    {
                        Card card = new Card();
                        card.setBalance(resultSet.getDouble("balance"));
                        card.setCardholderName(resultSet.getString("cardholder_name"));
                        card.setIban(resultSet.getString("iban"));
                        card.setCardNumber(resultSet.getString("card_number"));
                        card.setCvv(resultSet.getString("cvv"));
                        card.setExpirationDate(resultSet.getString("expiration_date"));
                        card.setPin(resultSet.getString("pin"));
                        return card;
                    }
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        throw new RuntimeException("Failed to retrieve card details by ID: " + id);
    }

    private void saveInDatabase(long senderCardId, long receiverCardId, Transaction transaction) {
        try (Connection connection = dataSource.getConnection())
        {
            String insertSql = "INSERT INTO transaction (sender_iban, receiver_iban, amount, timestamp, sender_card_id, receiver_card_id) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql))
            {
                preparedStatement.setString(1, transaction.getSenderIban());
                preparedStatement.setString(2, transaction.getReceiverIban());
                preparedStatement.setDouble(3, transaction.getAmount());
                preparedStatement.setString(4, transaction.getTimestamp());
                preparedStatement.setLong(6, senderCardId);
                preparedStatement.setLong(5, receiverCardId);
                preparedStatement.executeUpdate();
            }
            updateBalance(senderCardId, getCardById(senderCardId), -transaction.getAmount());
            updateBalance(receiverCardId, getCardById(receiverCardId), transaction.getAmount());
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void updateBalance(long cardId, Card card, double amount) {
        try (Connection connection = dataSource.getConnection())
        {
            String updateSql = "UPDATE card SET balance = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql))
            {
                preparedStatement.setDouble(1, card.getBalance() + amount);
                preparedStatement.setLong(2, cardId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        dataSource.close(); // Close the connection pool when done
    }
}
