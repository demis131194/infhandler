package by.epamgroup.infhandler.exception;

public class ComponentHandlerException extends Exception {

    public ComponentHandlerException() {
        super();
    }

    public ComponentHandlerException(String message) {
        super(message);
    }

    public ComponentHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComponentHandlerException(Throwable cause) {
        super(cause);
    }
}
