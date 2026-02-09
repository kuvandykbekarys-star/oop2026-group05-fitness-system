package MembershipComponent;

import PersistenceComponent.Repository;

import java.time.LocalDate;

public interface MemberRepository extends Repository<Member, Long> {
    void updateMembership(long memberId, long typeId, LocalDate start, LocalDate end);
}
