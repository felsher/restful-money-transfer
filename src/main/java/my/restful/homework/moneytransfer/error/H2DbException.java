package my.restful.homework.moneytransfer.error;

public class H2DbException extends Exception {

    public H2DbException() {
    }

    public H2DbException(String message) {
        super(message);
    }

    public H2DbException(String message, Throwable cause) {
        super(message, cause);
    }

}
