package tech.bonda.PaymentBackEnd.core.Generate;

import com.github.javafaker.Faker;
import org.mindrot.jbcrypt.BCrypt;
import tech.bonda.PaymentBackEnd.entities.account.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AccountGenerator {
    private static final Faker faker = new Faker();
    private static final Faker fakerBG = new Faker(Locale.of("bg"));

    public static void main(String[] args) {
        Scanner Scanner = new Scanner(System.in);
        int n = Scanner.nextInt();
        for (int i = 0; i < n; i++)
        {
            AccountGenerator accountGenerator = new AccountGenerator();

            Account account = accountGenerator.generateRandomAccount();
            showAccountDetails(account);
            System.out.println(checkIBAN("BG81BNPA94404518558798"));
            //accountGenerator.saveInDatabase(account);
        }
    }

    private Account generateRandomAccount() {
        Account account = new Account();
        account.setName(generateName());
        account.setEmail(generateEmail(account.getName()));
        generateUsernameAndPassword(account);
        account.setPhoneNumber(generatePhoneNumber());
        account.setEgn(generateEGN(0, 0, generateRandomYear(), 0, 0));
        account.setDateOfCreation(generateDate());
        return account;
    }

    private static String generateName() {
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    private static String generateEmail(String name) {
        String[] nameParts = name.split(" ");
        String firstName = nameParts[0];
        String lastName = nameParts[nameParts.length - 1];
        return faker.internet().emailAddress(firstName + "." + lastName);
    }

    private void generateUsernameAndPassword(Account account) {
        String username = faker.name().username();
        String password = "1234"; //faker.internet().password();
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
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return currentDate.format(dateFormatter);
    }

    private static void showAccountDetails(Account account) {
        System.out.println("Generated Account Details:");
        System.out.println("Name: " + account.getName());
        System.out.println("Email: " + account.getEmail());
        System.out.println("Username: " + account.getUsername());
        System.out.println("Password: " + account.getPassword());
        System.out.println("Phone Number: " + account.getPhoneNumber());
        System.out.println("EGN: " + account.getEgn());
        System.out.println("Date of Creation: " + account.getDateOfCreation());
    }

    private void saveInDatabase(Account account) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = Connector.getConnection();
            String insertSql = "INSERT INTO account (name, email, username, password, phone_number, egn, date_of_creation) VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertSql);
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

    private static int checkIBAN(String strIBAN) {
        int i = 1;
        String kod = "";

        // Define the Bis_Ascii_arr using a List of Strings
        List<String> bisAscii = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", // 13
                "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", // 26
                "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", // 37
                "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", // 48
                "32", "33", "34", "35")); // 51

        String strIBANNew = strIBAN.replace(" ", "");

        // Check if the first and second characters are digits
        if (!Character.isDigit(strIBANNew.charAt(0)) || !Character.isDigit(strIBANNew.charAt(1)))
        {
            return 0;
        }

        // Reorder the characters in strIBANNew
        strIBANNew = strIBANNew.substring(4) + strIBANNew.substring(0, 4);

        String ibanNum = "";
        if (strIBANNew != null)
        {
            for (i = 0; i < strIBANNew.length(); i++)
            {
                String numb = "";
                for (int j = 0; j < 26; j++)
                {
                    if (Character.toUpperCase(strIBANNew.charAt(i)) == bisAscii.get(j).charAt(0))
                    {
                        numb = bisAscii.get(j + 26);
                        break;
                    }
                }

                if (numb != null)
                {
                    ibanNum += numb;
                }
                else
                {
                    ibanNum += Character.toUpperCase(strIBANNew.charAt(i));
                }
            }
        }

        int ostat = mod(Integer.parseInt(ibanNum.substring(0, 9)), 97);
        i = 10;

        while (i <= ibanNum.length())
        {
            if (ostat == 0)
            {
                ostat = mod(Integer.parseInt(ibanNum.substring(i, i + 9)), 97);
                i += 9;
            }
            else if (Integer.toString(ostat).length() == 1)
            {
                ostat = mod(Integer.parseInt(ostat + ibanNum.substring(i, i + 8)), 97);
                i += 8;
            }
            else if (Integer.toString(ostat).length() == 2)
            {
                ostat = mod(Integer.parseInt(ostat + ibanNum.substring(i, i + 7)), 97);
                i += 7;
            }
        }

        int lastI = 0;
        if (lastI < ibanNum.length())
        {
            if (ostat >= 97)
            {
                ostat = mod(ostat, 97);
            }
            else
            {
                ostat = Integer.parseInt(ibanNum.substring(lastI + 1));
            }
        }

        //System.out.println("5.ostat=" + ostat);

        if (ostat == 1)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    private static int mod(int a, int b) {
        return a % b;
    }


}

