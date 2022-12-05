package io.github.edsonisaac.beemonitor.repositories;

import io.github.edsonisaac.beemonitor.entities.Mensuration;
import io.github.edsonisaac.beemonitor.projections.MensurationProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * The interface Mensuration repository.
 *
 * @author Edson Isaac
 */
@Repository
public interface MensurationRepository extends JpaRepository<Mensuration, UUID> {

    /**
     * Delete by hive.
     *
     * @param id the id
     */
    @Modifying
    @Query("DELETE FROM tb_mensurations WHERE hive.id = ?1")
    void deleteByHive(UUID id);

    /**
     * Find by hive id page.
     *
     * @param hiveId   the hive id
     * @param pageable the pageable
     * @return the page
     */
    Page<MensurationProjection> findByHiveId(UUID hiveId, Pageable pageable);
}