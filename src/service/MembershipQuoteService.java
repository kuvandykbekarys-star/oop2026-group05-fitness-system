package service;

import config.GymConfig;
import entity.MembershipType;
import exception.NotFoundException;
import patterns.factory.MembershipPackage;
import patterns.factory.MembershipPackageFactory;
import repository.MembershipTypeRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class MembershipQuoteService {

    private final MembershipTypeRepository repo;
    private final MembershipPackageFactory factory = new MembershipPackageFactory();
    private final GymConfig config = GymConfig.getInstance();

    public MembershipQuoteService(MembershipTypeRepository repo) {
        this.repo = repo;
    }

    public Quote quote(long typeId, boolean student, LocalDate start) {
        MembershipType t = repo.findById(typeId)
                .orElseThrow(() -> new NotFoundException("MembershipType not found"));

        MembershipPackage p = factory.create(t);

        double discount = student ? config.getStudentDiscountPercent() : config.getDefaultDiscountPercent();

        BigDecimal multiplier = BigDecimal.valueOf(1.0 - discount / 100.0);
        BigDecimal finalPrice = p.price().multiply(multiplier).setScale(2, RoundingMode.HALF_UP);

        return new Quote(p.name(), start, p.endDate(start), finalPrice);
    }

    public record Quote(String name, LocalDate start, LocalDate end, BigDecimal price) {}
}
