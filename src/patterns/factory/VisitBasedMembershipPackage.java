package patterns.factory;

import java.math.BigDecimal;
import java.time.LocalDate;

public class VisitBasedMembershipPackage implements MembershipPackage {

    private final String name;
    private final int days;
    private final BigDecimal price;

    public VisitBasedMembershipPackage(String name, int days, BigDecimal price) {
        this.name = name;
        this.days = days;
        this.price = price;
    }

    public String name() {
        return name;
    }

    public LocalDate endDate(LocalDate start) {
        return start.plusDays(days - 1L);
    }

    public BigDecimal price() {
        return price;
    }
}
