package my.restful.homework.moneytransfer.entity;

import my.restful.homework.moneytransfer.error.AccountServiceException;

import java.math.BigDecimal;

public class Deposit {

    private int accountId;

    private BigDecimal amount;

    public Deposit() {
    }

    public Deposit(int id, BigDecimal amount) throws AccountServiceException {
        this.accountId = id;
        if (amount == null)
            throw new AccountServiceException("Deposit amount can not be null");
        this.amount = amount;
    }

    public int getAccountId() {
        return accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
