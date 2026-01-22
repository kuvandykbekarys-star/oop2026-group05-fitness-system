package edu.aitu.oop3.db;

import entity.Member;
import entity.MembershipType;
import exception.NotFoundException;
import repository.MemberRepository;
import repository.MembershipTypeRepository;

import java.time.LocalDate;

public class MembershipService {
    private final MemberRepository memberRepo;
    private final MembershipTypeRepository typeRepo;
    private final NotificationService notifier;

    public MembershipService(MemberRepository memberRepo,
                             MembershipTypeRepository typeRepo,
                             NotificationService notifier) {
        this.memberRepo = memberRepo;
        this.typeRepo = typeRepo;
        this.notifier = notifier;
    }

    public void buyOrExtendMembership(long memberId, long membershipTypeId) {
        Member m = memberRepo.findById(memberId)
                .orElseThrow(() -> new NotFoundException("Member not found: " + memberId));

        MembershipType t = typeRepo.findById(membershipTypeId)
                .orElseThrow(() -> new NotFoundException("MembershipType not found: " + membershipTypeId));

        LocalDate today = LocalDate.now();

        LocalDate start;
        if (m.getMembershipEnd() != null && !m.getMembershipEnd().isBefore(today)) {
            start = m.getMembershipEnd().plusDays(1);
        } else {
            start = today;
        }

        LocalDate end = start.plusDays(t.getDurationDays() - 1L);

        memberRepo.updateMembership(memberId, membershipTypeId, start, end);
        notifier.notify("Membership updated for memberId=" + memberId + " until " + end);
    }
}
