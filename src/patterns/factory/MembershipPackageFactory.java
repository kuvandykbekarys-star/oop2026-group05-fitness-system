package patterns.factory;

import entity.MembershipType;

import java.math.BigDecimal;

public class MembershipPackageFactory {

    public MembershipPackage create(MembershipType type) {
        String n = type.getName().toLowerCase();
        int d = type.getDurationDays();
        BigDecimal p = type.getPrice();

        if (n.contains("year") || d >= 300)
            return new YearlyMembershipPackage(type.getName(), d, p);

        if (n.contains("visit"))
            return new VisitBasedMembershipPackage(type.getName(), d, p);

        return new MonthlyMembershipPackage(type.getName(), d, p);
    }
}
