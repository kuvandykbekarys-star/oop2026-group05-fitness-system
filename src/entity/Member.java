package entity;

import java.time.LocalDate;

public class Member {
    private final long id;
    private final String fullName;
    private final String email;
    private final Long membershipTypeId;
    private final LocalDate membershipStart;
    private final LocalDate membershipEnd;
    private final String status;

    public Member(long id, String fullName, String email, Long membershipTypeId,
                  LocalDate membershipStart, LocalDate membershipEnd, String status) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.membershipTypeId = membershipTypeId;
        this.membershipStart = membershipStart;
        this.membershipEnd = membershipEnd;
        this.status = status;
    }

    public long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public Long getMembershipTypeId() { return membershipTypeId; }
    public LocalDate getMembershipStart() { return membershipStart; }
    public LocalDate getMembershipEnd() { return membershipEnd; }
    public String getStatus() { return status; }
}
