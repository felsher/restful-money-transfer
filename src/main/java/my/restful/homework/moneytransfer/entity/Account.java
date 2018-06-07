package my.restful.homework.moneytransfer.entity;

import java.math.BigDecimal;

public class Account extends Entity {

    private User owner;

    private BigDecimal balance;

    public Account(User owner, BigDecimal balance) {
        this(null, owner, balance);
    }

    public Account(Long id, User owner, BigDecimal balance) {
        super(id);
        this.owner = owner;
        this.balance = balance;
    }

    public User getOwner() {
        return owner;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
