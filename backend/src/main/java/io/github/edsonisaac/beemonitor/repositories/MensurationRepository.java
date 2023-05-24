package io.github.edsonisaac.beemonitor.repositories;

import io.github.edsonisaac.beemonitor.entities.Mensuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MensurationRepository extends JpaRepository<Mensuration, UUID> {

    @Query("SELECT m FROM tb_mensurations AS m INNER JOIN m.hive AS h ON h.id = ?1")
    Page<Mensuration> search(UUID hiveId, Pageable page);

    @Query("SELECT m FROM tb_mensurations AS m INNER JOIN m.hive as h ON h.id = ?1 AND cast(m.createdDate as string) LIKE concat('%', ?2, '%')")
    Page<Mensuration> search(UUID hiveId, String date, Pageable page);
}