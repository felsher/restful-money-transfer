package my.restful.homework.moneytransfer.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

abstract class AbstractDAOConfig {

    private static final String H2_DB_DRIVER = "org.h2.Driver";
    private static final String H2_DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "user";
    private static final String DB_PASSWORD = "password";

    Connection getDBConnection() throws ClassNotFoundException, SQLException {
        Class.forName(H2_DB_DRIVER);
        return DriverManager.getConnection(H2_DB_CONNECTION, DB_USER, DB_PASSWORD);
    }

}
