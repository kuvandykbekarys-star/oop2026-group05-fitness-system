package exception;

/**
 * Exception thrown when a fitness class has reached its maximum capacity.
 */
public class ClassFullException extends RuntimeException {
    public ClassFullException(String message) {
        super(message);
    }
}
