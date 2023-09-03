package tech.bonda.PaymentBackEnd.core.Generate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {

    public static Connection getConnection() throws SQLException {
        String jdbcUrl = "jdbc:mysql://link.rdb.superhosting.bg:3306/bondatec_bonda";
        String username = "bondatec_bonda";
        String password = "fMBX6RV4SAgGtiB";
        return DriverManager.getConnection(jdbcUrl, username, password);
    }
}
