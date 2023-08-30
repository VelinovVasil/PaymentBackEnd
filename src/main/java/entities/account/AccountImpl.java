package entities.account;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
@Entity
public class AccountImpl implements Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String iban;
    private String egn;
    private double interest;
    private double balance;
    private String date;

    public AccountImpl(String iban, String egn, double interest, double balance, String date) {
        this.setIban(iban);
        this.setEgn(egn);
        this.setInterest(interest);
        this.setBalance(balance);
        this.setDate(date);
    }

    public AccountImpl() {

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

        if (checkIBAN(iban) != 0) {

            throw new IllegalArgumentException("Invalid iban");

        }

        this.iban = iban;
    }

    private void setEgn(String egn) {

        if (checkEGN(egn) != 0) {
            throw new IllegalArgumentException("Invaliid egn");
        }

        this.egn = egn;
    }

    private void setInterest(double interest) {

        if (interest <= 0) {
            throw new IllegalArgumentException("Interest cannot be below or equal to zero");
        }

        this.interest = interest;
    }

    private static int checkEGN(String txt) {
        int[] tegla = {2, 4, 8, 5, 10, 9, 7, 3, 6};
        String egn = txt;
        int result = 0;

        for (int i = 0; i < egn.length(); i++) {
            if (egn.charAt(i) < '0' || egn.charAt(i) > '9') {
                return 1;
            }
        }

        if (egn.length() != 10) {
            return 1;
        } else {
            int day = 0;
            int month = 0;
            int year = 0;

            try {
                day = Integer.parseInt(egn.substring(4, 6));
            } catch (NumberFormatException e) {
                day = 0;
            }

            try {
                month = Integer.parseInt(egn.substring(2, 4));
            } catch (NumberFormatException e) {
                month = 0;
            }

            try {
                year = Integer.parseInt(egn.substring(0, 2));
            } catch (NumberFormatException e) {
                year = 0;
            }

            String cor_data = "";

            if (month < 20) {
                cor_data = month + "-" + day + "-" + (year + 1900);
            }

            if (month > 20 && month < 40) {
                cor_data = (month - 20) + "-" + day + "-" + (year + 1800);
            }

            if (month > 40) {
                cor_data = (month - 40) + "-" + day + "-" + (year + 2000);
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
            Date res = null;

            try {
                res = dateFormat.parse(cor_data);
            } catch (ParseException e) {
                return 1;
            }

            for (int i = 0; i < 9; i++) {
                result += (egn.charAt(i) - '0') * tegla[i];
            }

            int nPos = result % 11;

            if (nPos == 10) {
                nPos = 0;
            }

            if (nPos == Integer.parseInt(egn.substring(9))) {
                return 0;
            } else {
                return 1;
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
        if (!Character.isDigit(strIBANNew.charAt(0)) || !Character.isDigit(strIBANNew.charAt(1))) {
            return 0;
        }

        // Reorder the characters in strIBANNew
        strIBANNew = strIBANNew.substring(4) + strIBANNew.substring(0, 4);

        String ibanNum = "";
        if (strIBANNew != null) {
            for (i = 0; i < strIBANNew.length(); i++) {
                String numb = "";
                for (int j = 0; j < 26; j++) {
                    if (Character.toUpperCase(strIBANNew.charAt(i)) == bisAscii.get(j).charAt(0)) {
                        numb = bisAscii.get(j + 26);
                        break;
                    }
                }

                if (numb != null) {
                    ibanNum += numb;
                } else {
                    ibanNum += Character.toUpperCase(strIBANNew.charAt(i));
                }
            }
        }

        int ostat = mod(Integer.parseInt(ibanNum.substring(0, 9)), 97);
        i = 10;

        while (i <= ibanNum.length()) {
            if (ostat == 0) {
                ostat = mod(Integer.parseInt(ibanNum.substring(i, i + 9)), 97);
                i += 9;
            } else if (Integer.toString(ostat).length() == 1) {
                ostat = mod(Integer.parseInt(ostat + ibanNum.substring(i, i + 8)), 97);
                i += 8;
            } else if (Integer.toString(ostat).length() == 2) {
                ostat = mod(Integer.parseInt(ostat + ibanNum.substring(i, i + 7)), 97);
                i += 7;
            }
        }

        int lastI = 0;
        if (lastI < ibanNum.length()) {
            if (ostat >= 97) {
                ostat = mod(ostat, 97);
            } else {
                ostat = Integer.parseInt(ibanNum.substring(lastI + 1));
            }
        }

        System.out.println("5.ostat=" + ostat);

        if (ostat == 1) {
            return 1;
        } else {
            return 0;
        }
    }

    private static int mod(int a, int b) {
        return a % b;
    }
}
