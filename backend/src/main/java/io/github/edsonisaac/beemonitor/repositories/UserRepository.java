package io.github.edsonisaac.beemonitor.repositories;

import io.github.edsonisaac.beemonitor.entities.User;
import io.github.edsonisaac.beemonitor.projections.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
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
     * Find alll list.
     *
     * @return the list
     */
    @Query("SELECT u FROM tb_users AS u")
    List<UserProjection> findAlll();

    /**
     * Find by username optional.
     *
     * @param username the username
     * @return the optional
     */
    Optional<UserProjection> findByUsername (String username);
}