package MembershipComponent;

import PersistenceComponent.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMembershipTypeRepository implements MembershipTypeRepository {

    private final DatabaseConnection db;

    public JdbcMembershipTypeRepository(DatabaseConnection db) {
        this.db = db;
    }

    @Override
    public Optional<MembershipType> findById(Long id) {
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

    @Override
    public List<MembershipType> findAll() {
        String sql = """
                select id, name, duration_days, price
                from membership_types
                order by id
                """;
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<MembershipType> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new MembershipType(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("duration_days"),
                        rs.getBigDecimal("price")
                ));
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
