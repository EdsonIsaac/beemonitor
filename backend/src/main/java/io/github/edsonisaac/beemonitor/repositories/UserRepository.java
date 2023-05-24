package io.github.edsonisaac.beemonitor.repositories;

import io.github.edsonisaac.beemonitor.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
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
    Optional<User> findByUsername(@Param("username") String username);
}