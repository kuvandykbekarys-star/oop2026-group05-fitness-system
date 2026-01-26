package exception;

/**
 * Exception thrown when a user tries to book a class they are already registered for.
 */
public class BookingAlreadyExistsException extends RuntimeException {
    public BookingAlreadyExistsException(String message) {
        super(message);
    }
}
