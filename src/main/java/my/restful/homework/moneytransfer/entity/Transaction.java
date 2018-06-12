package my.restful.homework.moneytransfer.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Transaction {

    private BigDecimal amount;

    private int fromAccountId;

    private int toAccountId;

    public Transaction() {
    }

    @JsonCreator
    public Transaction(
            @JsonProperty("amount") BigDecimal amount,
            @JsonProperty("fromAccountId") int fromAccountId,
            @JsonProperty("toAccountId") int toAccountId) {
        this.amount = amount;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public int getFromAccountId() {
        return fromAccountId;
    }

    public int getToAccountId() {
        return toAccountId;
    }
}
