package tech.bonda.PaymentBackEnd.entities.account;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardHolderName;
    private String iban;
    private String cardNumber;
    private String cvv;
    private String expirationDate;
    private String pin;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Card() {
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

        System.out.println("5.ostat=" + ostat);

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

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
