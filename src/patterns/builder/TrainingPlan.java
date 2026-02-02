// Implementation of Builder pattern for complex training plans [cite: 61, 81]
package patterns.builder;

import java.util.ArrayList;
import java.util.List;

public class TrainingPlan {

    private final long memberId;
    private final String goal;
    private final int weeks;
    private final List<Long> classIds;

    private TrainingPlan(Builder b) {
        this.memberId = b.memberId;
        this.goal = b.goal;
        this.weeks = b.weeks;
        this.classIds = List.copyOf(b.classIds);
    }

    public static Builder builder(long memberId) {
        return new Builder(memberId);
    }

    public static class Builder {

        private final long memberId;
        private String goal;
        private int weeks;
        private final List<Long> classIds = new ArrayList<>();

        public Builder(long memberId) {
            this.memberId = memberId;
        }

        public Builder goal(String goal) {
            this.goal = goal;
            return this;
        }

        public Builder weeks(int weeks) {
            this.weeks = weeks;
            return this;
        }

        public Builder addClass(long classId) {
            classIds.add(classId);
            return this;
        }

        public TrainingPlan build() {
            if (goal == null || weeks <= 0)
                throw new IllegalStateException();
            return new TrainingPlan(this);
        }
    }
}
