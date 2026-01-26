package exception;

/**
 * Exception thrown when a member tries to access services with an expired membership.
 */
public class MembershipExpiredException extends RuntimeException {
    public MembershipExpiredException(String message) {
        super(message);
    }
}
