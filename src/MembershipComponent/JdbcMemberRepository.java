package MembershipComponent;

import PersistenceComponent.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMemberRepository implements MemberRepository {

    private final DatabaseConnection db;

    public JdbcMemberRepository(DatabaseConnection db) {
        this.db = db;
    }

    @Override
    public Optional<Member> findById(Long id) {
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
    public List<Member> findAll() {
        String sql = """
                select id, full_name, email, membership_type_id, membership_start, membership_end, status
                from members
                order by id
                """;

        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Member> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Member(
                        rs.getLong("id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        (Long) rs.getObject("membership_type_id"),
                        rs.getObject("membership_start", LocalDate.class),
                        rs.getObject("membership_end", LocalDate.class),
                        rs.getString("status")
                ));
            }
            return list;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateMembership(long memberId, long typeId, LocalDate start, LocalDate end) {
        String sql = """
                update members
                set membership_type_id = ?, membership_start = ?, membership_end = ?, status = 'ACTIVE'
                where id = ?
                """;

        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, typeId);
            ps.setObject(2, start);
            ps.setObject(3, end);
            ps.setLong(4, memberId);

            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
