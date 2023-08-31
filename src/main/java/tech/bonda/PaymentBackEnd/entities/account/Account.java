package tech.bonda.PaymentBackEnd.entities.account;

import jakarta.persistence.*;
import lombok.Getter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@Getter
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String egn;
    private String dateOfCreation;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Collection<Card> cards;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Collection<Transaction> transactions;

    public Account() {
    }

    private static int checkEGN(String txt) {
        int[] tegla = {2, 4, 8, 5, 10, 9, 7, 3, 6};
        String egn = txt;
        int result = 0;

        for (int i = 0; i < egn.length(); i++)
        {
            if (egn.charAt(i) < '0' || egn.charAt(i) > '9')
            {
                return 1;
            }
        }

        if (egn.length() != 10)
        {
            return 1;
        }
        else
        {
            int day = 0;
            int month = 0;
            int year = 0;

            try
            {
                day = Integer.parseInt(egn.substring(4, 6));
            } catch (NumberFormatException e)
            {
                day = 0;
            }

            try
            {
                month = Integer.parseInt(egn.substring(2, 4));
            } catch (NumberFormatException e)
            {
                month = 0;
            }

            try
            {
                year = Integer.parseInt(egn.substring(0, 2));
            } catch (NumberFormatException e)
            {
                year = 0;
            }

            String cor_data = "";

            if (month < 20)
            {
                cor_data = month + "-" + day + "-" + (year + 1900);
            }

            if (month > 20 && month < 40)
            {
                cor_data = (month - 20) + "-" + day + "-" + (year + 1800);
            }

            if (month > 40)
            {
                cor_data = (month - 40) + "-" + day + "-" + (year + 2000);
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
            Date res = null;

            try
            {
                res = dateFormat.parse(cor_data);
            } catch (ParseException e)
            {
                return 1;
            }

            for (int i = 0; i < 9; i++)
            {
                result += (egn.charAt(i) - '0') * tegla[i];
            }

            int nPos = result % 11;

            if (nPos == 10)
            {
                nPos = 0;
            }

            if (nPos == Integer.parseInt(egn.substring(9)))
            {
                return 0;
            }
            else
            {
                return 1;
            }
        }
    }

    public void setName(String name) {

        if (name.length() < 3)
        {
            throw new IllegalArgumentException("Name must be at least 3 characters long");
        }

        this.name = name;
    }

    public void setEgn(String egn) {

        if (checkEGN(egn) != 0)
        {
            throw new IllegalArgumentException("Invaliid egn");
        }

        this.egn = egn;
    }
}
