package io.github.edsonisaac.beemonitor.repositories;

import io.github.edsonisaac.beemonitor.entities.Mensuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

/**
 * The interface Mensuration repository.
 *
 * @author Edson Isaac
 */
@Repository
public interface MensurationRepository extends JpaRepository<Mensuration, UUID> {

    /**
     * Find all by hive id page.
     *
     * @param hiveId the hive id
     * @param page   the page
     * @return the page
     */
    @Query("SELECT m FROM tb_mensurations AS m INNER JOIN m.hive AS h ON h.id = ?1")
    Page<Mensuration> findAll(UUID hiveId, Pageable page);

    /**
     * Find by hive page.
     *
     * @param hiveId the hive id
     * @param page   the page
     * @return the page
     */
    @Query("SELECT m FROM tb_mensurations AS m INNER JOIN m.hive as h ON h.id = ?1 AND cast(m.createdDate as string) LIKE concat('%', ?2, '%')")
    Page<Mensuration> search(UUID hiveId, String date, Pageable page);
}