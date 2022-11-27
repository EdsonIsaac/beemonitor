package io.github.edsonisaac.beemonitor.repositories;

import io.github.edsonisaac.beemonitor.entities.Mensuration;
import io.github.edsonisaac.beemonitor.projections.MensurationProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * The interface Mensuration repository.
 *
 * @author Edson Isaac
 */
@Repository
public interface MensurationRepository extends JpaRepository<Mensuration, UUID> {

    /**
     * Find by hive id page.
     *
     * @param hiveId   the hive id
     * @param pageable the pageable
     * @return the page
     */
    Page<MensurationProjection> findByHiveId(UUID hiveId, Pageable pageable);
}