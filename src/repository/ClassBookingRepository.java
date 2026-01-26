package repository;

import entity.ClassBooking;

import java.util.List;

public interface ClassBookingRepository {
    int countBookedForClass(long classId);
    boolean existsBooked(long memberId, long classId);
    void createBooked(long memberId, long classId);
    List<ClassBooking> findByMember(long memberId);
}
