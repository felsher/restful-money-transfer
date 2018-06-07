package my.restful.homework.moneytransfer.dao;

import my.restful.homework.moneytransfer.entity.Account;
import my.restful.homework.moneytransfer.entity.Transaction;
import my.restful.homework.moneytransfer.entity.User;
import org.apache.commons.dbutils.DbUtils;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO extends AbstractDAOConfig {

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS Account(id UUID PRIMARY KEY NOT NULL, ownerId UUID, balance DECIMAL(16,4));";
    private static final String INSERT_ACCOUNT_QUERY = "INSERT INTO Account(id, ownerId, balance) VALUES (?, ?, ?);";
    private static final String SELECT_ACCOUNT_QUERY = "select * from Account;";

    private void createAccountTable(Connection dbConnection) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = dbConnection.prepareStatement(CREATE_TABLE_QUERY);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            close(null, preparedStatement, null);
        }
    }

    public void transfer(Transaction transaction) {

    }

    public void insertAccount(Account account) {
        Connection connection = null;
        PreparedStatement insertPreparedStatement = null;
        try {
            connection = getDBConnection();
            createAccountTable(connection);

            insertPreparedStatement = connection.prepareStatement(INSERT_ACCOUNT_QUERY);
            insertPreparedStatement.setObject(1, account.getUuid());
            insertPreparedStatement.setObject(2, account.getOwner().getUuid());
            insertPreparedStatement.setBigDecimal(3, account.getBalance());
            insertPreparedStatement.executeUpdate();

            connection.commit();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(
                    String.format("Exception occured in insertAccount method -> Account UUID:[%s] -> %s", account.getUuid(), e.getMessage())
            );
        } finally {
            close(connection, insertPreparedStatement, null);
        }
    }

    public List<Account> findAllAccounts() {
        Connection connection = null;
        PreparedStatement selectStatement = null;
        ResultSet resultSet = null;

        List<Account> result = new ArrayList<>();

        try {
            connection = getDBConnection();
            selectStatement = connection.prepareStatement(SELECT_ACCOUNT_QUERY);
            resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                String accountId = resultSet.getObject("id").toString();
                String ownerId = resultSet.getObject("ownerId").toString();
                BigDecimal balance = resultSet.getBigDecimal("balance");
                Account account = new Account(accountId, new User(ownerId, null, null), balance);
                result.add(account);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(
                    String.format("Exception occured in findAllAccounts method -> %s", e.getMessage())
            );
        } finally {
            close(connection, selectStatement, resultSet);
        }
        return result;
    }

    private void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            DbUtils.close(connection);
        } catch (SQLException e) {
            System.err.println(
                    String.format("Failed to close connection -> %s", e.getMessage())
            );
        }
        try {
            DbUtils.close(preparedStatement);
        } catch (SQLException e) {
            System.err.println(
                    String.format("Failed to close prepared statement -> %s", e.getMessage())
            );
        }
        try {
            DbUtils.close(resultSet);
        } catch (SQLException e) {
            System.err.println(
                    String.format("Failed to close result set -> %s", e.getMessage())
            );
        }
    }

}
