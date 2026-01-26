package entity;

import java.time.OffsetDateTime;

public class ClassBooking {
    private final long id;
    private final long memberId;
    private final long classId;
    private final OffsetDateTime bookedAt;
    private final String status;

    public ClassBooking(long id, long memberId, long classId, OffsetDateTime bookedAt, String status) {
        this.id = id;
        this.memberId = memberId;
        this.classId = classId;
        this.bookedAt = bookedAt;
        this.status = status;
    }

    public long getId() { return id; }
    public long getMemberId() { return memberId; }
    public long getClassId() { return classId; }
    public OffsetDateTime getBookedAt() { return bookedAt; }
    public String getStatus() { return status; }
}
.