package my.restful.homework.moneytransfer.entity;

import my.restful.homework.moneytransfer.error.AccountServiceException;

import java.math.BigDecimal;
import java.math.MathContext;

public class Account {

    private int id;

    private BigDecimal balance;

    public Account() {
    }

    public Account(int id, BigDecimal balance) throws AccountServiceException {
        this.id = id;
        if (balance == null)
            throw new AccountServiceException("Account can not have null balance");
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public boolean hasSufficientBalance(BigDecimal amount) {
        int compareResult = amount.compareTo(this.balance);
        return compareResult < 1;
    }

    public void withdraw(BigDecimal amount) throws AccountServiceException {
        if (!hasSufficientBalance(amount)) {
            throw new AccountServiceException("Account do not have sufficient balance to withdraw");
        }
        this.balance = this.balance.subtract(
                amount,
                new MathContext(16)
        );
    }

    public void deposit(BigDecimal amount) {
        this.balance = this.balance.add(
                amount,
                new MathContext(16)
        );
    }
}
