package entity;

import java.math.BigDecimal;

public class MembershipType {
    private final long id;
    private final String name;
    private final int durationDays;
    private final BigDecimal price;

    public MembershipType(long id, String name, int durationDays, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.durationDays = durationDays;
        this.price = price;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public int getDurationDays() { return durationDays; }
    public BigDecimal getPrice() { return price; }
}
.