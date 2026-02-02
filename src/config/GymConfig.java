package config;

public final class GymConfig {

    private static final GymConfig INSTANCE = new GymConfig();

    private final double studentDiscountPercent;
    private final double defaultDiscountPercent;

    private GymConfig() {
        this.studentDiscountPercent = 10.0;
        this.defaultDiscountPercent = 0.0;
    }

    public static GymConfig getInstance() {
        return INSTANCE;
    }

    public double getStudentDiscountPercent() {
        return studentDiscountPercent;
    }

    public double getDefaultDiscountPercent() {
        return defaultDiscountPercent;
    }
}
