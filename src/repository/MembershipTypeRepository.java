package repository;

import entity.MembershipType;

import java.util.Optional;

/**
 * Interface for retrieving membership type information from the database.
 */
public interface MembershipTypeRepository {
    Optional<MembershipType> findById(long id);
}
.