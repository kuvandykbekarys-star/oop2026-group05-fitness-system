package repository;

import entity.FitnessClass;

import java.util.Optional;

public interface FitnessClassRepository {
    Optional<FitnessClass> findById(long id);
}
.