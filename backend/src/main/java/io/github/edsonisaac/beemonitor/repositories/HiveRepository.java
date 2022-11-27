package io.github.edsonisaac.beemonitor.repositories;

import io.github.edsonisaac.beemonitor.entities.Hive;
import io.github.edsonisaac.beemonitor.projections.HiveProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
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
     * Find alll list.
     *
     * @return the list
     */
    @Query("SELECT h FROM tb_hives AS h")
    List<HiveProjection> findAlll();

    /**
     * Find by code optional.
     *
     * @param code the code
     * @return the optional
     */
    Optional<HiveProjection> findByCode (String code);
}