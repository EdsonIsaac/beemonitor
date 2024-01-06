package io.github.edsonisaac.beemonitor.repositories;

import io.github.edsonisaac.beemonitor.models.Hive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * The HiveRepository interface provides CRUD operations for managing Hive entities.
 * It extends the JpaRepository interface, which provides generic CRUD methods
 * for interacting with the underlying data store.
 *
 * @author Edson Isaac
 */
@Repository
public interface HiveRepository extends JpaRepository<Hive, UUID> {

    /**
     * Finds a hive by its unique code.
     *
     * @param code the code to search for
     * @return An Optional containing the hive if found, or empty if not found
     */
    Optional<Hive> findByCode(String code);

    /**
     * Searches for hives by their code containing the specified value in a case-insensitive manner.
     *
     * @param value the value to search for in the hive's code
     * @param page the pagination information
     * @return A Page containing the list of hives that match the search criteria
     */
    @Query("SELECT h FROM tb_hives AS h WHERE upper(h.code) LIKE upper(concat('%', ?1, '%'))")
    Page<Hive> search(String value, Pageable page);
}
