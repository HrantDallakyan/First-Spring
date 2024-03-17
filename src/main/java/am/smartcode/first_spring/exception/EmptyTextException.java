package am.smartcode.first_spring.exception;

public class EmptyTextException extends RuntimeException {

    public EmptyTextException(String message) {
        super(message);
    }
}
