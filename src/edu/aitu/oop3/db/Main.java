package edu.aitu.oop3.db;

import exception.BookingAlreadyExistsException;
import exception.ClassFullException;
import exception.MembershipExpiredException;
import repository.ClassBookingRepository;
import repository.FitnessClassRepository;
import repository.MemberRepository;
import repository.MembershipTypeRepository;
import repository.jdbc.JdbcClassBookingRepository;
import repository.jdbc.JdbcFitnessClassRepository;
import repository.jdbc.JdbcMemberRepository;
import repository.jdbc.JdbcMembershipTypeRepository;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://aws-1-us-west-1.pooler.supabase.com:5432/postgres?sslmode=require";
        String user = "postgres.rqmqsrenkhboaelmelad";
        String password = "A9b#u#?+PLa@i?D";

        DatabaseConnection db = new DatabaseConnection(url, user, password);

        NotificationService notifier = new NotificationService();

        MemberRepository memberRepo = new JdbcMemberRepository(db);
        MembershipTypeRepository typeRepo = new JdbcMembershipTypeRepository(db);
        FitnessClassRepository classRepo = new JdbcFitnessClassRepository(db);
        ClassBookingRepository bookingRepo = new JdbcClassBookingRepository(db);

        MembershipService membershipService =
                new MembershipService(memberRepo, typeRepo, notifier);

        BookingService bookingService =
                new BookingService(memberRepo, classRepo, bookingRepo, notifier);

        long memberId = 1;
        long membershipTypeId = 1;
        long classId = 1;

        membershipService.buyOrExtendMembership(memberId, membershipTypeId);

        try {
            bookingService.bookClass(memberId, classId);
        } catch (MembershipExpiredException |
                 ClassFullException |
                 BookingAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }

        try {
            bookingService.bookClass(memberId, classId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        bookingRepo.findByMember(memberId)
                .forEach(b ->
                        System.out.println(
                                b.getId() + " " +
                                        b.getClassId() + " " +
                                        b.getStatus()
                        )
                );
    }
}
