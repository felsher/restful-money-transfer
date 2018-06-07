package my.restful.homework.moneytransfer.dao;

import my.restful.homework.moneytransfer.error.H2DbException;

import java.sql.*;

import static my.restful.homework.moneytransfer.dao.H2DbConfig.DB_PASSWORD;
import static my.restful.homework.moneytransfer.dao.H2DbConfig.DB_USER;
import static my.restful.homework.moneytransfer.dao.H2DbConfig.H2_DB_CONNECTION;

public class DbUtils {

    static Connection getDbConnection() throws SQLException {
        return DriverManager.getConnection(H2_DB_CONNECTION, DB_USER, DB_PASSWORD);
    }

    static void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) throws H2DbException {
        try {
            org.apache.commons.dbutils.DbUtils.close(connection);
        } catch (SQLException e) {
            throw new H2DbException(String.format("Failed to close connection -> %s", e.getMessage()), e);
        }
        try {
            org.apache.commons.dbutils.DbUtils.close(preparedStatement);
        } catch (SQLException e) {
            throw new H2DbException(String.format("Failed to close prepared statement -> %s", e.getMessage()), e);
        }
        try {
            org.apache.commons.dbutils.DbUtils.close(resultSet);
        } catch (SQLException e) {
            throw new H2DbException(String.format("Failed to close result set -> %s", e.getMessage()), e);
        }
    }
}
