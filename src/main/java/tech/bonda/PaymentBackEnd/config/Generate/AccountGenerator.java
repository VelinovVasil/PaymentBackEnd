package tech.bonda.PaymentBackEnd.config.Generate;

import com.github.javafaker.Faker;
import org.mindrot.jbcrypt.BCrypt;
import tech.bonda.PaymentBackEnd.entities.account.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class AccountGenerator {
    private static final Faker faker = new Faker();
    private static final Faker fakerBG = new Faker(Locale.forLanguageTag("bg"));

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of accounts to generate: ");
        int n = scanner.nextInt();
        for (int i = 0; i < n; i++)
        {
            AccountGenerator accountGenerator = new AccountGenerator();
            Account account = accountGenerator.generateRandomAccount();
            showAccountDetails(account);
            accountGenerator.saveInDatabase(account);
        }
    }

    private Account generateRandomAccount() {
        Account account = new Account();
        account.setName(generateName());
        account.setEmail(generateEmail(account.getName()));
        generateUsernameAndPassword(account);
        account.setPhoneNumber(generatePhoneNumber());
        account.setEgn(generateEGN(0, 0, 0, 0, 0));
        account.setDateOfCreation(generateDate());
        return account;
    }

    private static String generateName() {
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    private static String generateEmail(String name) {
        String[] nameParts = name.split(" ");
        String firstName = nameParts[0].toLowerCase();
        String lastName = nameParts[nameParts.length - 1].toLowerCase();
        return faker.internet().emailAddress(firstName + "." + lastName);
    }

    private void generateUsernameAndPassword(Account account) {
        String username = account.getName().toLowerCase().replace(" ", "");
        String password = "1234"; // Default password for all accounts
        password = BCrypt.hashpw(password, BCrypt.gensalt());
        account.setUsername(username);
        account.setPassword(password);
    }

    private static String generatePhoneNumber() {
        return phoneNumberFormatter(fakerBG.phoneNumber().cellPhone());
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

        if (year < 1980 || year > 2099)
        {
            year = 1980 + new Random().nextInt(300);
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


    private static String generateDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return currentDate.format(dateFormatter);
    }

    public static void showAccountDetails(Account account) {
        System.out.println("Generated Account Details:");
        System.out.println("Name: " + account.getName());
        System.out.println("Email: " + account.getEmail());
        System.out.println("Username: " + account.getUsername());
        System.out.println("Password: " + account.getPassword());
        System.out.println("Phone Number: " + account.getPhoneNumber());
        System.out.println("EGN: " + account.getEgn());
        System.out.println("Date of Creation: " + account.getDateOfCreation());
    }

    private static String phoneNumberFormatter(String input) {
        String resultString = "(+359)" + input.substring(1);
        return resultString.substring(0, 6) + " " + resultString.substring(6, 8) + " " + resultString.substring(8, 11) + " " + resultString.substring(11);
    }

    private void saveInDatabase(Account account) {
        try (Connection connection = Connector.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO account (name, email, username, password, phone_number, egn, date_of_creation) " + "VALUES (?, ?, ?, ?, ?, ?, ?)"))
        {

            preparedStatement.setString(1, account.getName());
            preparedStatement.setString(2, account.getEmail());
            preparedStatement.setString(3, account.getUsername());
            preparedStatement.setString(4, account.getPassword());
            preparedStatement.setString(5, account.getPhoneNumber());
            preparedStatement.setString(6, account.getEgn());
            preparedStatement.setString(7, account.getDateOfCreation());

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
        }
    }
}
