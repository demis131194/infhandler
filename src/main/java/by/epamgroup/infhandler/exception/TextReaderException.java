package by.epamgroup.infhandler.exception;

public class TextReaderException extends Exception{
    public TextReaderException() {
        super();
    }

    public TextReaderException(String message) {
        super(message);
    }

    public TextReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public TextReaderException(Throwable cause) {
        super(cause);
    }
}
