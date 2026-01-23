package repository.jdbc;

import edu.aitu.oop3.db.DatabaseConnection;
import entity.ClassBooking;
import exception.BookingAlreadyExistsException;
import repository.ClassBookingRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcClassBookingRepository implements ClassBookingRepository {
    private final DatabaseConnection db;

    public JdbcClassBookingRepository(DatabaseConnection db) {
        this.db = db;
    }

    @Override
    public int countBookedForClass(long classId) {
        String sql = "select count(*) from class_bookings where class_id = ? and status <> 'CANCELLED'";
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, classId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsBooked(long memberId, long classId) {
        String sql = """
                select 1
                from class_bookings
                where member_id = ? and class_id = ? and status <> 'CANCELLED'
                limit 1
                """;
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, memberId);
            ps.setLong(2, classId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createBooked(long memberId, long classId) {
        String sql = "insert into class_bookings(member_id, class_id, status) values (?, ?, 'BOOKED')";
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, memberId);
            ps.setLong(2, classId);
            ps.executeUpdate();
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                throw new BookingAlreadyExistsException(
                        "Booking already exists for memberId=" + memberId + ", classId=" + classId
                );
            }
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ClassBooking> findByMember(long memberId) {
        String sql = """
                select id, member_id, class_id, booked_at, status
                from class_bookings
                where member_id = ?
                order by booked_at desc
                """;
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, memberId);
            try (ResultSet rs = ps.executeQuery()) {
                List<ClassBooking> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new ClassBooking(
                            rs.getLong("id"),
                            rs.getLong("member_id"),
                            rs.getLong("class_id"),
                            rs.getObject("booked_at", OffsetDateTime.class),
                            rs.getString("status")
                    ));
                }
                return list;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
