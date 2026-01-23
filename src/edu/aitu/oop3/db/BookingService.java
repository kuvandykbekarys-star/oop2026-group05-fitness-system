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

        Member member = memberRepo.findById(memberId)
                .orElseThrow(() -> new NotFoundException("Member not found: " + memberId));

        FitnessClass fitnessClass = classRepo.findById(classId)
                .orElseThrow(() -> new NotFoundException("Class not found: " + classId));

        LocalDate today = LocalDate.now();

        if (member.getMembershipEnd() == null ||
                member.getMembershipEnd().isBefore(today) ||
                "EXPIRED".equalsIgnoreCase(member.getStatus())) {
            throw new MembershipExpiredException("Membership expired for memberId=" + memberId);
        }

        if (bookingRepo.existsBooked(memberId, classId)) {
            throw new BookingAlreadyExistsException(
                    "Booking already exists for memberId=" + memberId + ", classId=" + classId
            );
        }

        if (bookingRepo.countBookedForClass(classId) >= fitnessClass.getCapacity()) {
            throw new ClassFullException(
                    "Class is full: classId=" + classId + ", capacity=" + fitnessClass.getCapacity()
            );
        }

        bookingRepo.createBooked(memberId, classId);
        notifier.notify("Class booked for memberId=" + memberId + ", classId=" + classId);
    }

    public void viewAttendanceHistory(long memberId) {

        memberRepo.findById(memberId)
                .orElseThrow(() -> new NotFoundException("Member not found: " + memberId));

        bookingRepo.findByMember(memberId).forEach(b ->
                System.out.println(
                        b.getId() + " " +
                                b.getClassId() + " " +
                                b.getStatus() + " " +
                                b.getBookedAt()
                )
        );
    }
}
