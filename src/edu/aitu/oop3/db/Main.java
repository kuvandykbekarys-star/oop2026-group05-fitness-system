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

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String url = "jdbc:postgresql://aws-1-us-west-1.pooler.supabase.com:5432/postgres?sslmode=require";
        String user = "postgres.rqmqsrenkhboaelmelad";
        String password = "A9b#u#?+PLa@i?D";

        DatabaseConnection db = new DatabaseConnection(url, user, password);

        try (var c = db.getConnection();
             var st = c.createStatement();
             var rs = st.executeQuery("select current_user, current_database()")) {

            rs.next();
            System.out.println("CONNECTED TO DB: " +
                    rs.getString(1) + " / " + rs.getString(2));

        } catch (Exception e) {
            System.out.println("DATABASE CONNECTION FAILED:");
            e.printStackTrace();
            return;
        }

        NotificationService notifier = new NotificationService();

        MemberRepository memberRepo = new JdbcMemberRepository(db);
        MembershipTypeRepository typeRepo = new JdbcMembershipTypeRepository(db);
        FitnessClassRepository classRepo = new JdbcFitnessClassRepository(db);
        ClassBookingRepository bookingRepo = new JdbcClassBookingRepository(db);

        MembershipService membershipService =
                new MembershipService(memberRepo, typeRepo, notifier);

        BookingService bookingService =
                new BookingService(memberRepo, classRepo, bookingRepo, notifier);

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your member ID: ");
        long memberId = sc.nextLong();

        System.out.print("Enter membership type ID (buy or extend): ");
        long membershipTypeId = sc.nextLong();

        try {
            membershipService.buyOrExtendMembership(memberId, membershipTypeId);
        } catch (Exception e) {
            System.out.println("Membership error: " + e.getMessage());
            return;
        }

        System.out.print("Enter class ID to book: ");
        long classId = sc.nextLong();

        try {
            bookingService.bookClass(memberId, classId);
            System.out.println("Class booked successfully.");
        } catch (MembershipExpiredException |
                 ClassFullException |
                 BookingAlreadyExistsException e) {
            System.out.println("Booking error: " + e.getMessage());
        }

        System.out.println("\n--- ATTENDANCE HISTORY ---");
        bookingService.viewAttendanceHistory(memberId);
    }

}
