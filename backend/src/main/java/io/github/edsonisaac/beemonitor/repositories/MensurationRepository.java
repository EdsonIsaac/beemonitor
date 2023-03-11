package io.github.edsonisaac.beemonitor.repositories;

import io.github.edsonisaac.beemonitor.entities.Mensuration;
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
     * Find by hive page.
     *
     * @param hiveId the hive id
     * @param page   the page
     * @return the page
     */
    Page<Mensuration> findByHiveId(UUID hiveId, Pageable page);
}