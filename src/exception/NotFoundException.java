package exception;

/**
 * General exception for cases where a requested resource (member, class, etc.) is not found.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
