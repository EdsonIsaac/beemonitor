package io.github.edsonisaac.beemonitor.repositories;

import io.github.edsonisaac.beemonitor.entities.Mensuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * The MensurationRepository interface provides CRUD operations for managing Mensuration entities.
 * It extends the JpaRepository interface, which provides generic CRUD methods
 * for interacting with the underlying data store.
 *
 * @author Edson Isaac
 */
@Repository
public interface MensurationRepository extends JpaRepository<Mensuration, UUID> {

    /**
     * Searches for mensuration's associated with a specific hive and containing a given value in the created date.
     *
     * @param hiveId the ID of the hive to search mensuration's for
     * @param value  the value to search for in the created date of mensuration's
     * @param page   the pagination information
     * @return A Page containing the list of mensuration's that match the search criteria
     */
    @Query(
        value = "SELECT m FROM tb_mensurations AS m JOIN FETCH m.hive as h " +
            "WHERE h.id = ?1 " +
            "AND cast(m.createdDate as string) LIKE concat('%', ?2, '%')",

        countQuery = "SELECT COUNT(m) FROM tb_mensurations  AS m JOIN m.hive AS h " +
            "WHERE h.id = ?1 " +
            "AND cast(m.createdDate as string) LIKE concat('%', ?2, '%')"
    )
    Page<Mensuration> search(UUID hiveId, String value, Pageable page);
}