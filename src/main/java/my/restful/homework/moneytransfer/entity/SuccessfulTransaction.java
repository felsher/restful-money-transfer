package my.restful.homework.moneytransfer.entity;

public class SuccessfulTransaction {

    private final Long successfulTransactionId;

    public SuccessfulTransaction(Long successfulTransactionId) {
        this.successfulTransactionId = successfulTransactionId;
    }

    public Long getSuccessfulTransactionId() {
        return successfulTransactionId;
    }
}
