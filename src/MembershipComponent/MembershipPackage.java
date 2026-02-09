package MembershipComponent;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface MembershipPackage {
    String name();
    LocalDate endDate(LocalDate start);
    BigDecimal price();
}

