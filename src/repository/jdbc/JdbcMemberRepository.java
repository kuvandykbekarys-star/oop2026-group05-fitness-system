package repository.jdbc;

import edu.aitu.oop3.db.DatabaseConnection;
import entity.Member;
import repository.MemberRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Optional;

public class JdbcMemberRepository implements MemberRepository {
    private final DatabaseConnection db;

    public JdbcMemberRepository(DatabaseConnection db) {
        this.db = db;
    }

    @Override
    public Optional<Member> findById(long id) {
        String sql = """
                select id, full_name, email, membership_type_id, membership_start, membership_end, status
                from members
                where id = ?
                """;
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(new Member(
                        rs.getLong("id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        (Long) rs.getObject("membership_type_id"),
                        rs.getObject("membership_start", LocalDate.class),
                        rs.getObject("membership_end", LocalDate.class),
                        rs.getString("status")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateMembership(long memberId, long membershipTypeId, LocalDate start, LocalDate end) {
        String sql = """
                update members
                set membership_type_id = ?, membership_start = ?, membership_end = ?, status = 'ACTIVE'
                where id = ?
                """;
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, membershipTypeId);
            ps.setObject(2, start);
            ps.setObject(3, end);
            ps.setLong(4, memberId);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void markExpired(long memberId) {
        String sql = "update members set status = 'EXPIRED' where id = ?";
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, memberId);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
