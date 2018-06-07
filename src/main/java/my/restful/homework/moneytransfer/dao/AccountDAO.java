package my.restful.homework.moneytransfer.dao;

import my.restful.homework.moneytransfer.entity.Account;
import my.restful.homework.moneytransfer.entity.SuccessfulTransaction;
import my.restful.homework.moneytransfer.entity.Transaction;
import my.restful.homework.moneytransfer.entity.User;
import my.restful.homework.moneytransfer.error.H2DbException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static my.restful.homework.moneytransfer.dao.DbUtils.close;
import static my.restful.homework.moneytransfer.dao.DbUtils.getDbConnection;

public class AccountDAO {

    private static final String CREATE_ACCOUNT_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS ACCOUNT(id LONG PRIMARY KEY AUTO_INCREMENT NOT NULL, ownerId LONG, balance DECIMAL(16,4));";
    private static final String INSERT_ACCOUNT_QUERY = "INSERT INTO ACCOUNT(ownerId, balance) VALUES (?, ?);";
    private static final String SELECT_ALL_ACCOUNT_QUERY = "select * from ACCOUNT;";

    private void createAccountTable(Connection dbConnection) throws H2DbException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = dbConnection.prepareStatement(CREATE_ACCOUNT_TABLE_QUERY);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            close(null, preparedStatement, null);
        }
    }

    /**
     * @return generated account id
     */
    public Long insertAccount(Account account) throws H2DbException {
        Connection connection = null;
        PreparedStatement stmnt = null;
        ResultSet resSet = null;
        try {
            connection = getDbConnection();
            createAccountTable(connection);

            stmnt = connection.prepareStatement(INSERT_ACCOUNT_QUERY, Statement.RETURN_GENERATED_KEYS);
            stmnt.setLong(1, account.getOwner().getId());
            stmnt.setBigDecimal(2, account.getBalance());
            stmnt.executeUpdate();

            resSet = stmnt.getGeneratedKeys();

            if (resSet.next())
                return resSet.getLong(1);
            else
                throw new H2DbException(
                        String.format("Account insert failed, no id obtained -> Account owner id: %s", account.getOwner().getId())
                );
        } catch (SQLException e) {
            throw new H2DbException(
                    String.format("Exception occured in Account insert method -> Account owner: %s -> %s", account.getOwner().getId(), e.getMessage()),
                    e
            );
        } finally {
            close(connection, stmnt, resSet);
        }
    }

    public List<Account> findAllAccounts() throws H2DbException {
        Connection connection = null;
        PreparedStatement selectStatement = null;
        ResultSet resultSet = null;

        List<Account> result = new ArrayList<>();

        try {
            connection = getDbConnection();
            selectStatement = connection.prepareStatement(SELECT_ALL_ACCOUNT_QUERY);
            resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                Long accountId = resultSet.getLong("id");
                Long ownerId = resultSet.getLong("ownerId");
                BigDecimal balance = resultSet.getBigDecimal("balance");

                Account account = new Account(accountId, new User(ownerId), balance);

                result.add(account);
            }
            return result;
        } catch (SQLException e) {
            throw new H2DbException("Exception occured in findAllAccounts method", e);
        } finally {
            close(connection, selectStatement, resultSet);
        }
    }

    public static class AccountDaoHolder {
        public static final AccountDAO ACCOUNT_DAO = new AccountDAO();
    }

    public static AccountDAO getAccountDao() {
        return AccountDaoHolder.ACCOUNT_DAO;
    }
}
