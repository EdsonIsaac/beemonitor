package io.github.edsonisaac.beemonitor.repositories;

import io.github.edsonisaac.beemonitor.entities.Hive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * The interface Hive repository.
 *
 * @author Edson Isaac
 */
@Repository
public interface HiveRepository extends JpaRepository<Hive, UUID> {

    /**
     * Find by code optional.
     *
     * @param code the code
     * @return the optional
     */
    Optional<Hive> findByCode (String code);
}