package by.epamgroup.infhandler.exception;

public class IllegalExpressionException extends Exception {

    public IllegalExpressionException() {
        super();
    }

    public IllegalExpressionException(String message) {
        super(message);
    }

    public IllegalExpressionException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalExpressionException(Throwable cause) {
        super(cause);
    }
}
