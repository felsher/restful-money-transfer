package my.restful.homework.moneytransfer.entity;

import java.math.BigDecimal;
import java.util.UUID;

public class Account extends Entity {

    private User owner;

    private BigDecimal balance;

    public Account(User owner, BigDecimal balance) {
        this(UUID.randomUUID().toString(), owner, balance);
    }

    public Account(String id, User owner, BigDecimal balance) {
        super(id);
        this.owner = owner;
        this.balance = balance;
    }

    public User getOwner() {
        return owner;
    }

    public Account setOwner(User owner) {
        this.owner = owner;
        return this;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Account setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }
}
