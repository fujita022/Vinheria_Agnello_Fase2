package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private static final String USER = "RM557351";
    private static final String PASSWORD = "020205";

    public static Connection getConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Oracle Driver not found!", e);
        } catch (SQLException e) {
            throw new RuntimeException("Erro in database connection", e);
        }
    }
}