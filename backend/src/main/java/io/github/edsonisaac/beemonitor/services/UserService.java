package io.github.edsonisaac.beemonitor.services;

import io.github.edsonisaac.beemonitor.entities.User;
import io.github.edsonisaac.beemonitor.exceptions.ObjectNotFoundException;
import io.github.edsonisaac.beemonitor.exceptions.ValidationException;
import io.github.edsonisaac.beemonitor.projections.UserProjection;
import io.github.edsonisaac.beemonitor.repositories.UserRepository;
import io.github.edsonisaac.beemonitor.utils.MessageUtils;
import io.github.edsonisaac.beemonitor.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type User service.
 *
 * @author Edson Isaac
 */
@Service
public class UserService {

    private final UserRepository repository;

    /**
     * Instantiates a new User service.
     *
     * @param repository the repository
     */
    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<UserProjection> findAll() {
        return repository.findAlll();
    }

    /**
     * Find by username user projection.
     *
     * @param username the username
     * @return the user projection
     */
    public UserProjection findByUsername (String username) {

        return repository.findByUsername(username).orElseThrow(() -> {
            throw new ObjectNotFoundException(MessageUtils.USER_NOT_FOUND);
        });
    }

    /**
     * Save user.
     *
     * @param user the user
     * @return the user
     */
    public User save (User user) {

        if (user == null) {
            throw new ValidationException(MessageUtils.USER_NULL);
        }

        if (validateUser(user)) {
            repository.save(user);
        }

        return user;
    }

    /**
     * Validate User
     *
     * @param user the user
     * @return the boolean
     */
    private boolean validateUser (User user) {

        var user_findByUsername = repository.findByUsername(user.getUsername());

        if (user_findByUsername.isPresent()) {

            if (!user.equals(UserUtils.toUser(user_findByUsername.get()))) {
                throw new ValidationException(MessageUtils.USER_ALREADY_SAVE);
            }
        }

        return true;
    }
}