package ClassBookingComponent.entity;

import java.time.OffsetDateTime;

public class FitnessClass {
    private final long id;
    private final String title;
    private final String coach;
    private final OffsetDateTime startTime;
    private final OffsetDateTime endTime;
    private final int capacity;

    public FitnessClass(long id, String title, String coach,
                        OffsetDateTime startTime, OffsetDateTime endTime, int capacity) {
        this.id = id;
        this.title = title;
        this.coach = coach;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
    }

    public long getId() { return id; }
    public String getTitle() { return title; }
    public String getCoach() { return coach; }
    public OffsetDateTime getStartTime() { return startTime; }
    public OffsetDateTime getEndTime() { return endTime; }
    public int getCapacity() { return capacity; }
}
