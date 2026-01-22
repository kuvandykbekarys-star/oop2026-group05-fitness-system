package repository;

import entity.MembershipType;

import java.util.Optional;

public interface MembershipTypeRepository {
    Optional<MembershipType> findById(long id);
}
