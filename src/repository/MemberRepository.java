package repository;

import entity.Member;

import java.time.LocalDate;
import java.util.Optional;

public interface MemberRepository {
    Optional<Member> findById(long id);
    void updateMembership(long memberId, long membershipTypeId, LocalDate start, LocalDate end);
    void markExpired(long memberId);
}
