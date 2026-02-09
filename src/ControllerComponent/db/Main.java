package ControllerComponent.db;

import ClassBookingComponent.entity.BookingService;
import MembershipComponent.MembershipService;
import ClassBookingComponent.entity.BookingAlreadyExistsException;
import ClassBookingComponent.entity.repository.ClassBookingRepository;
import ClassBookingComponent.entity.repository.FitnessClassRepository;
import MembershipComponent.MemberRepository;
import MembershipComponent.MembershipTypeRepository;
import ClassBookingComponent.entity.repository.jdbc.JdbcClassBookingRepository;
import ClassBookingComponent.entity.repository.jdbc.JdbcFitnessClassRepository;
import MembershipComponent.JdbcMemberRepository;
import MembershipComponent.JdbcMembershipTypeRepository;
import MembershipComponent.service.MembershipQuoteService;
import NotificationComponent.NotificationService;
import PersistenceComponent.DatabaseConnection;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String url = "jdbc:postgresql://aws-1-us-west-1.pooler.supabase.com:5432/postgres?sslmode=require";
        String user = "postgres.rqmqsrenkhboaelmelad";
        String password = "A9b#u#?+PLa@i?D";

        DatabaseConnection db = new DatabaseConnection(url, user, password);

        MemberRepository memberRepo = new JdbcMemberRepository(db);
        MembershipTypeRepository typeRepo = new JdbcMembershipTypeRepository(db);
        FitnessClassRepository classRepo = new JdbcFitnessClassRepository(db);
        ClassBookingRepository bookingRepo = new JdbcClassBookingRepository(db);

        NotificationService notifier = new NotificationService();

        MembershipService membershipService =
                new MembershipService(memberRepo, typeRepo, notifier);

        BookingService bookingService =
                new BookingService(memberRepo, classRepo, bookingRepo, notifier);

        MembershipQuoteService quoteService =
                new MembershipQuoteService(typeRepo);

        Scanner sc = new Scanner(System.in);

        System.out.print("Member ID: ");
        long memberId = sc.nextLong();

        System.out.print("Membership type ID: ");
        long typeId = sc.nextLong();

        var quote = quoteService.quote(typeId, false, LocalDate.now());
        System.out.println(
                quote.name() + " " +
                        quote.start() + " " +
                        quote.end() + " " +
                        quote.price()
        );

        membershipService.buyOrExtendMembership(memberId, typeId);
        var classes = classRepo.findAll();

        var filtered = util.filter.Filters.filter(
                classes,
                c -> c.getCapacity() >= 10
        );

        for (var c : filtered) {
            System.out.println(c.getId() + " " + c.getTitle() + " " + c.getCoach());
        }


        System.out.print("Class ID to book: ");
        long classId = sc.nextLong();

        try {
            bookingService.bookClass(memberId, classId);
            System.out.println("Booked successfully");
        } catch (BookingAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }

        bookingService.viewAttendanceHistory(memberId);

        sc.close();
    }
}
