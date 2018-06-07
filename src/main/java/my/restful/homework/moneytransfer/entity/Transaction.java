package my.restful.homework.moneytransfer.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.UUID;

public class Transaction extends Entity {

    private BigDecimal amount;

    private Long fromAccountId;

    private Long toAccountId;

    @JsonCreator
    public Transaction(
            @JsonProperty("amount") BigDecimal amount,
            @JsonProperty("fromAccountId") Long fromAccountId,
            @JsonProperty("toAccountId") Long toAccountId) {
        super(null);
        this.amount = amount;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
    }

    public Transaction(Long id, BigDecimal amount, Long fromAccountId, Long toAccountId) {
        super(id);
        this.amount = amount;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }
}
