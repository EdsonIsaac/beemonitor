package io.github.edsonisaac.beemonitor.repositories;

import io.github.edsonisaac.beemonitor.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * The UserRepository interface provides CRUD operations for managing User entities.
 * It extends the JpaRepository interface, which provides generic CRUD methods
 * for interacting with the underlying data store.
 *
 * @author Edson Isaac
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Finds a user by their username.
     *
     * @param username the username to search for
     * @return an Optional containing the user if found, or empty if not found
     */
    Optional<User> findByUsername(String username);

    /**
     * Searches for users by their name or username containing the specified value.
     *
     * @param value the value to search for in the user's name or username
     * @param page the pagination information
     * @return A Page containing the list of users that match the search criteria
     */
    @Query("SELECT u FROM tb_users AS u " +
            "WHERE upper(u.name) LIKE upper(concat('%', ?1, '%')) " +
            "OR upper(u.username) LIKE upper(concat('%', ?1, '%'))")
    Page<User> search(String value, Pageable page);
}