package io.github.edsonisaac.beemonitor.repositories;

import io.github.edsonisaac.beemonitor.entities.Hive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HiveRepository extends JpaRepository<Hive, UUID> {

    Optional<Hive> findByCode (String code);

    @Query("SELECT h FROM tb_hives AS h WHERE upper(h.code) LIKE upper(concat('%', ?1, '%'))")
    Page<Hive> search(String value, Pageable page);
}