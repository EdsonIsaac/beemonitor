package io.github.edsonisaac.beemonitor.repositories;

import io.github.edsonisaac.beemonitor.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * The interface User repository.
 *
 * @author Edson Isaac
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Find by username optional.
     *
     * @param username the username
     * @return the optional
     */
    Optional<User> findByUsername (String username);


    /**
     * Search page.
     *
     * @param value the value
     * @param page  the page
     * @return the page
     */
    @Query("SELECT u FROM tb_users AS u WHERE upper(u.name) LIKE upper(concat('%', ?1, '%')) OR upper(u.username) LIKE upper(concat('%', ?1, '%'))")
    Page<User> search(String value, Pageable page);
}