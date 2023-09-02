package tech.bonda.PaymentBackEnd.core.Generate;

import com.github.javafaker.Faker;
import tech.bonda.PaymentBackEnd.entities.account.Account;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class AccountGenerator {
    public static void main(String[] args) {
        AccountGenerator accountGenerator = new AccountGenerator();

        Account account = accountGenerator.generateRandomAccount();
        System.out.println("Generated Account Details:");
        System.out.println("Name: " + account.getName());
        System.out.println("Phone Number: " + account.getPhoneNumber());
        System.out.println("EGN: " + account.getEgn());
        System.out.println("Date of Creation: " + account.getDateOfCreation());

        accountGenerator.saveInDatabase(account);
    }

    private static String generateEGN(int day, int month, int year, int gender, int region) {
        final int[] weights = {2, 4, 8, 5, 10, 9, 7, 3, 6};

        final int[] regionsLastNum = {43, 93, 139, 169, 183, 217, 233, 281, 301, 319, 341, 377, 395, 435, 501, 527, 555, 575, 601, 623, 721, 751, 789, 821, 843, 871, 903, 925, 999};


        if (day <= 0 || day > 31)
        {
            day = new Random().nextInt(31) + 1;
        }

        if (month <= 0 || month > 12)
        {
            month = new Random().nextInt(12) + 1;
        }

        if (year < 1800 || year > 2099)
        {
            year = 1800 + new Random().nextInt(300);
        }

        if (region < 0 || region >= regionsLastNum.length)
        {
            region = new Random().nextInt(regionsLastNum.length);
        }

        int birthNumber = 1 + new Random().nextInt(regionsLastNum[region] - region);

        if (gender != 1 && gender != 2)
        {
            gender = new Random().nextInt(2) + 1;
        }

        String egn = String.format("%02d%02d%02d%03d", year % 100, month, day, region + (birthNumber - 1) * 2 + (gender - 1));

        int checksum = 0;
        for (int i = 0; i < 9; i++)
        {
            checksum += (egn.charAt(i) - '0') * weights[i];
        }
        checksum = checksum % 11;
        if (checksum == 10)
        {
            checksum = 0;
        }

        return egn + checksum;
    }

    private static int generateRandomYear() {
        int currentYear = Year.now().getValue() - 18;
        int minYear = 1960;

        Random random = new Random();

        return random.nextInt(currentYear - minYear + 1) + minYear;
    }

    private static String phoneNumberFormatter(String input) {
        String resultString = "(+359)" + input.substring(1);

        resultString = resultString.substring(0, 6) + " " + resultString.substring(6, 8) + " " + resultString.substring(8, 11) + " " + resultString.substring(11);

        return resultString;
    }

    private static String generateDate() {
        Faker faker = new Faker();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, faker.number().numberBetween(2020, 2024));
        Date randomDate = faker.date().between(calendar.getTime(), calendar.getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(randomDate);
    }

    public Connection getConnection() throws SQLException {
        String jdbcUrl = "jdbc:mysql://link.rdb.superhosting.bg:3306/bondatec_bonda";
        String username = "bondatec_bonda";
        String password = "fMBX6RV4SAgGtiB";
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    private void saveInDatabase(Account account) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = getConnection();
            String insertSql = "INSERT INTO account (name, phone_number, egn, date_of_creation) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setString(1, account.getName());
            preparedStatement.setString(2, account.getPhoneNumber());
            preparedStatement.setString(3, account.getEgn());
            preparedStatement.setString(4, account.getDateOfCreation());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0)
            {
                System.out.println("Account saved to the database.");
            }
            else
            {
                System.out.println("Failed to save account to the database.");
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

    private Account generateRandomAccount() {
        Faker faker = new Faker();
        Faker fakerBG = new Faker(Locale.of("bg"));

        Account account = new Account();
        account.setName(faker.name().fullName());
        account.setPhoneNumber(phoneNumberFormatter(fakerBG.phoneNumber().cellPhone()));
        account.setEgn(generateEGN(0, 0, generateRandomYear(), 0, 0));
        account.setDateOfCreation(generateDate());
        return account;
    }

}

