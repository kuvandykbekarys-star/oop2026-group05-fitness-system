package repository.jdbc;

import edu.aitu.oop3.db.DatabaseConnection;
import entity.FitnessClass;
import repository.FitnessClassRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcFitnessClassRepository implements FitnessClassRepository {

    private final DatabaseConnection db;

    public JdbcFitnessClassRepository(DatabaseConnection db) {
        this.db = db;
    }

    @Override
    public Optional<FitnessClass> findById(Long id) {
        String sql = "select id, title, coach, start_time, end_time, capacity from classes where id = ?";
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new FitnessClass(
                            rs.getLong("id"),
                            rs.getString("title"),
                            rs.getString("coach"),
                            rs.getObject("start_time", OffsetDateTime.class),
                            rs.getObject("end_time", OffsetDateTime.class),
                            rs.getInt("capacity")
                    ));
                }
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<FitnessClass> findAll() {
        String sql = "select id, title, coach, start_time, end_time, capacity from classes order by id";
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<FitnessClass> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new FitnessClass(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("coach"),
                        rs.getObject("start_time", OffsetDateTime.class),
                        rs.getObject("end_time", OffsetDateTime.class),
                        rs.getInt("capacity")
                ));
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
