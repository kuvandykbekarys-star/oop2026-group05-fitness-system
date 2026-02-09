package ClassBookingComponent.entity;


public class MembershipExpiredException extends RuntimeException {
    public MembershipExpiredException(String message) {
        super(message);
    }
}
