package repository;

import entity.Member;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Interface for managing member data and their membership status.
 */
public interface MemberRepository {
    Optional<Member> findById(long id);
    void updateMembership(long memberId, long membershipTypeId, LocalDate start, LocalDate end);
    void markExpired(long memberId);
}
