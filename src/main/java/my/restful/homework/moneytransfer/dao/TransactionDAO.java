package my.restful.homework.moneytransfer.dao;

import my.restful.homework.moneytransfer.entity.Transaction;
import my.restful.homework.moneytransfer.error.H2DbException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static my.restful.homework.moneytransfer.dao.DbUtils.close;
import static my.restful.homework.moneytransfer.dao.DbUtils.getDbConnection;

public class TransactionDAO {

    private static final String CREATE_TRANSACTION_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS TRANSACTION(id LONG PRIMARY KEY AUTO_INCREMENT NOT NULL, fromAccountId LONG, toAccountId LONG, amount DECIMAL(16,4));";
    private static final String INSERT_TRANSACTION_QUERY = "INSERT INTO TRANSACTION(fromAccountId, toAccountId, amount) VALUES (?, ?, ?);";
    private static final String SELECT_ALL_TRANSACTION_QUERY = "select * from TRANSACTION;";

    private void createTransactionTable(Connection dbConnection) throws H2DbException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = dbConnection.prepareStatement(CREATE_TRANSACTION_TABLE_QUERY);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            close(null, preparedStatement, null);
        }
    }

    /**
     * @return generated account id
     */
    public Long insertTransaction(Transaction transaction) throws H2DbException {
        Connection connection = null;
        PreparedStatement stmnt = null;
        ResultSet resSet = null;
        try {
            connection = getDbConnection();
            createTransactionTable(connection);

            stmnt = connection.prepareStatement(INSERT_TRANSACTION_QUERY, Statement.RETURN_GENERATED_KEYS);
            stmnt.setLong(1, transaction.getFromAccountId());
            stmnt.setLong(2, transaction.getToAccountId());
            stmnt.setBigDecimal(3, transaction.getAmount());
            stmnt.executeUpdate();

            resSet = stmnt.getGeneratedKeys();

            if (resSet.next())
                return resSet.getLong(1);
            else
                throw new H2DbException(
                        String.format("Transaction insert failed, no id obtained -> Transaction fromAccountId: %s, toAccountId: %s",
                                transaction.getFromAccountId(), transaction.getToAccountId())
                );
        } catch (SQLException e) {
            throw new H2DbException(
                    String.format("Exception occured in Transaction insert method -> Transaction fromAccountId: %s, toAccountId: %s",
                            transaction.getFromAccountId(), transaction.getToAccountId()),
                    e
            );
        } finally {
            close(connection, stmnt, resSet);
        }
    }

    public List<Transaction> findAllTransactions() throws H2DbException {
        Connection connection = null;
        PreparedStatement selectStatement = null;
        ResultSet resultSet = null;

        List<Transaction> result = new ArrayList<>();

        try {
            connection = getDbConnection();
            selectStatement = connection.prepareStatement(SELECT_ALL_TRANSACTION_QUERY);
            resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                Long transactionId = resultSet.getLong("id");
                Long fromAccountId = resultSet.getLong("fromAccountId");
                Long toAccountId = resultSet.getLong("toAccountId");
                BigDecimal amount = resultSet.getBigDecimal("amount");

                Transaction transaction = new Transaction(transactionId, amount, fromAccountId, toAccountId);
                result.add(transaction);
            }
            return result;
        } catch (SQLException e) {
            throw new H2DbException("Exception occured in findAllTransactions method", e);
        } finally {
            close(connection, selectStatement, resultSet);
        }
    }

    public static class TransactionDaoHolder {
        public static final TransactionDAO TRANSACTION_DAO = new TransactionDAO();
    }

    public static TransactionDAO getTransactionDao() {
        return TransactionDaoHolder.TRANSACTION_DAO;
    }
}
