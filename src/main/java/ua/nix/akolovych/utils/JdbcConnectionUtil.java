package ua.nix.akolovych.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class JdbcConnectionUtil {
    private static Connection connection;

    public static Connection getConnection(String url, String user, String password) throws SQLException {
        if(connection == null){
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }

    private JdbcConnectionUtil(){}
}
