package edu.aitu.oop3.db;

import entity.FitnessClass;
import entity.Member;
import exception.BookingAlreadyExistsException;
import exception.ClassFullException;
import exception.MembershipExpiredException;
import exception.NotFoundException;
import repository.ClassBookingRepository;
import repository.FitnessClassRepository;
import repository.MemberRepository;

import java.time.LocalDate;

public class BookingService {
    private final MemberRepository memberRepo;
    private final FitnessClassRepository classRepo;
    private final ClassBookingRepository bookingRepo;
    private final NotificationService notifier;

    public BookingService(MemberRepository memberRepo,
                          FitnessClassRepository classRepo,
                          ClassBookingRepository bookingRepo,
                          NotificationService notifier) {
        this.memberRepo = memberRepo;
        this.classRepo = classRepo;
        this.bookingRepo = bookingRepo;
        this.notifier = notifier;
    }

    public void bookClass(long memberId, long classId) {
        Member m = memberRepo.findById(memberId)
                .orElseThrow(() -> new NotFoundException("Member not found: " + memberId));

        FitnessClass fc = classRepo.findById(classId)
                .orElseThrow(() -> new NotFoundException("Class not found: " + classId));

        LocalDate today = LocalDate.now();
        if (m.getMembershipEnd() == null || m.getMembershipEnd().isBefore(today) || "EXPIRED".equalsIgnoreCase(m.getStatus())) {
            throw new MembershipExpiredException("Membership expired for memberId=" + memberId);
        }

        if (bookingRepo.existsBooked(memberId, classId)) {
            throw new BookingAlreadyExistsException("Booking already exists for memberId=" + memberId + ", classId=" + classId);
        }

        int booked = bookingRepo.countBookedForClass(classId);
        if (booked >= fc.getCapacity()) {
            throw new ClassFullException("Class is full: classId=" + classId + " (capacity=" + fc.getCapacity() + ")");
        }

        bookingRepo.createBooked(memberId, classId);
        notifier.notify("Booked classId=" + classId + " for memberId=" + memberId);
    }
}
