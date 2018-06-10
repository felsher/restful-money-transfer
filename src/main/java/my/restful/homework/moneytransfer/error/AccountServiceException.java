package my.restful.homework.moneytransfer.error;

public class AccountServiceException extends Exception {

    public AccountServiceException() {
    }

    public AccountServiceException(String message) {
        super(message);
    }

    public AccountServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
