package my.restful.homework.moneytransfer.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.UUID;

public class Transaction extends Entity {

    private BigDecimal amount;

    private String fromAccountId;

    private String toAccountId;

    @JsonCreator
    Transaction(
            @JsonProperty("amount") BigDecimal amount,
            @JsonProperty("fromAccountId") String fromAccountId,
            @JsonProperty("toAccountId") String toAccountId) {
        super(UUID.randomUUID().toString());
        this.amount = amount;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Transaction setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public String getFromAccountId() {
        return fromAccountId;
    }

    public Transaction setFromAccountId(String fromAccountId) {
        this.fromAccountId = fromAccountId;
        return this;
    }

    public String getToAccountId() {
        return toAccountId;
    }

    public Transaction setToAccountId(String toAccountId) {
        this.toAccountId = toAccountId;
        return this;
    }
}
