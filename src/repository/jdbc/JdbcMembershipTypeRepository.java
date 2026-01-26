package repository.jdbc;

import edu.aitu.oop3.db.DatabaseConnection;
import entity.MembershipType;
import repository.MembershipTypeRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class JdbcMembershipTypeRepository implements MembershipTypeRepository {
    private final DatabaseConnection db;

    public JdbcMembershipTypeRepository(DatabaseConnection db) {
        this.db = db;
    }

    @Override
    public Optional<MembershipType> findById(long id) {
        String sql = """
                select id, name, duration_days, price
                from membership_types
                where id = ?
                """;
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(new MembershipType(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("duration_days"),
                        rs.getBigDecimal("price")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
.